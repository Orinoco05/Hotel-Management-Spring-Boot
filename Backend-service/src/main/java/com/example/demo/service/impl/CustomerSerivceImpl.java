package com.example.demo.service.impl;

import com.example.demo.common.Status;
import com.example.demo.controller.request.CustomerCreationRequest;
import com.example.demo.controller.request.CustomerPasswordRequest;
import com.example.demo.controller.request.CustomerUpdateRequest;
import com.example.demo.controller.response.CustomerPageResponse;
import com.example.demo.controller.response.CustomerResponse;
import com.example.demo.exception.InvalidDataException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.CustomerEntity;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j(topic = "CUSTOMMER-SERVICE")
@RequiredArgsConstructor
public class CustomerSerivceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public CustomerPageResponse findAll(String keyword, String sort, int page, int size) {


        //Sorting
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id"); // Mặc định sắp xếp theo id ASC

        if (StringUtils.hasLength(sort)) { // Kiểm tra sort không rỗng
            Pattern pattern = Pattern.compile("(\\w+?)(:)(asc|desc)", Pattern.CASE_INSENSITIVE); // Định dạng: tencot:asc/desc
            Matcher matcher = pattern.matcher(sort);

            if (matcher.find()) {
                String columnName = matcher.group(1); // Tên cột
                String direction = matcher.group(3);  // asc hoặc desc

                if ("asc".equalsIgnoreCase(direction)) {
                    order = new Sort.Order(Sort.Direction.ASC, columnName);
                } else if ("desc".equalsIgnoreCase(direction)) {
                    order = new Sort.Order(Sort.Direction.DESC, columnName);
                }
            } else {
                throw new IllegalArgumentException("Sort format is invalid. Expected format: 'column:asc' or 'column:desc'.");
            }
        }

        // Xu li truong hop FE muon bat dau voi page = 1
        int pageNo = 0;
        if (page > 0){
            pageNo = page - 1;
        }

        //Paging
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        Page<CustomerEntity> customerEntities;

        if (StringUtils.hasLength(keyword)) {
            keyword = "%" + keyword.toLowerCase() + "%";
            customerEntities =  customerRepository.searchByKeyword(keyword, pageable);

        } else {

            customerEntities = customerRepository.findAll(pageable);
        }

        return getCustomerPageResponse(page, size, customerEntities);
}

    @Override
    public CustomerResponse findById(Long id) {
        log.info("Find customer by id: {}", id);

        CustomerEntity customerEntity = getCustomerEntity(id);

        return CustomerResponse.builder()
                .id(id)
                .address(customerEntity.getAddress())
                .phone(customerEntity.getPhone())
                .name(customerEntity.getName())
                .status(customerEntity.getStatus())
                .gender(customerEntity.getGender())
                .nationality(customerEntity.getNationality())
                .email(customerEntity.getEmail())
                .citizen_identification_card(customerEntity.getCitizen_identification_card())
                .build();
    }

    @Override
    public CustomerResponse findByEmail(String email) {
        return null;
    }

    @Override
    public CustomerResponse findByUsername(String username) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long save(CustomerCreationRequest req) {
        log.info("Saving Customer: {}", req);

        CustomerEntity customerEmail = customerRepository.findByEmail(req.getEmail());
        if (customerEmail != null) {
            throw new InvalidDataException("Customer already exists.");
        }

        CustomerEntity customerEntity = new CustomerEntity();


        customerEntity.setEmail(req.getEmail());
        customerEntity.setName(req.getName());
        customerEntity.setAddress(req.getAddress());
        customerEntity.setPhone(req.getPhone());
        customerEntity.setCitizen_identification_card(req.getCitizen_identification_card());
        customerEntity.setGender(req.getGender());
        customerEntity.setNationality(req.getNationality());
        customerRepository.save(customerEntity);
        log.info("Saved Customer: {}", customerEntity);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CustomerUpdateRequest req) {
        log.info("Updating Customer: {}", req);

        // Get customer by id
        CustomerEntity customerEntity = getCustomerEntity(req.getId());
        customerEntity.setEmail(req.getEmail());
        customerEntity.setName(req.getName());
        customerEntity.setAddress(req.getAddress());
        customerEntity.setPhone(req.getPhone());
        customerEntity.setCitizen_identification_card(req.getCitizen_identification_card());
        customerEntity.setGender(req.getGender());
        customerEntity.setNationality(req.getNationality());

        customerRepository.save(customerEntity);

        log.info("Updated Customer: {}", customerEntity);

        // Set data

        // Save to db

    }

    @Override
    public void delete(Long id) {
        log.info("Deleting Customer: {}", id);

        CustomerEntity customerEntity = getCustomerEntity(id);
        customerEntity.setStatus(Status.INACTIVE);

        customerRepository.save(customerEntity);
        log.info("Deleted Customer: {}", customerEntity);
    }

    @Override
    public void changePassword(CustomerPasswordRequest req) {
        log.info("Changing Password: {}", req);

        // Get user by id
        CustomerEntity customer = getCustomerEntity(req.getId());
        if (req.getPassword().equals(req.getConfirmPassword())) {
            customer.setPassword(passwordEncoder.encode(req.getPassword()));

            customerRepository.save(customer);
            log.info("Changed Password: {}", customer);

        }
    }

    /**
     * Get customer by id
     * @param id
     * @return
     */
    private CustomerEntity getCustomerEntity(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    /**
     * Convert CustomerEntity to CustomerResponse
     * @param page
     * @param size
     * @param customerEntities
     * @return
     */
    private static CustomerPageResponse getCustomerPageResponse(int page, int size, Page<CustomerEntity> customerEntities) {
        log.info("Convert Customer Entity Page");
        List<CustomerResponse> customerList = customerEntities.stream()
                .map(customerEntity -> CustomerResponse.builder()
                        .id(customerEntity.getId())
                        .name(customerEntity.getName())
                        .phone(customerEntity.getPhone())
                        .address(customerEntity.getAddress())
                        .gender(customerEntity.getGender())
                        .status(customerEntity.getStatus())
                        .nationality(customerEntity.getNationality())
                        .citizen_identification_card(customerEntity.getCitizen_identification_card())
                        .email(customerEntity.getEmail())
                        .build())
                .toList();

        CustomerPageResponse response = new CustomerPageResponse();
        response.setPageNumber(page);
        response.setPageSize(size);
        response.setTotalElements(customerEntities.getTotalElements());
        response.setTotalPages(customerEntities.getTotalPages());
        response.setCustomers(customerList);
        return response;
    }
}
