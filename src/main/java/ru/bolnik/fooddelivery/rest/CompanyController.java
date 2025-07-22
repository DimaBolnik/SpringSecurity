package ru.bolnik.fooddelivery.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bolnik.fooddelivery.dto.CompanyDto;
import ru.bolnik.fooddelivery.dto.OrderDto;
import ru.bolnik.fooddelivery.dto.converters.CompanyDtoConverter;
import ru.bolnik.fooddelivery.dto.converters.OrderDtoConverter;
import ru.bolnik.fooddelivery.model.Company;
import ru.bolnik.fooddelivery.model.Order;
import ru.bolnik.fooddelivery.services.CompanyService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyDtoConverter companyDtoConverter;
    private final OrderDtoConverter orderDtoConverter;
    private final CompanyService companyService;

    @GetMapping("/company")
    public List<CompanyDto> getAll() {
        return companyService.getAll()
                .stream()
                .map(companyDtoConverter::toDto)
                .toList();
    }

    @GetMapping(value = "/company", params = "user-id")
    public List<CompanyDto> getAllByUser(@RequestParam("user-id") long userId) {
        return companyService.getAllByUserId(userId)
                .stream()
                .map(companyDtoConverter::toDto)
                .collect(toList());
    }

    @GetMapping("/company/{id}")
    public CompanyDto getById(@PathVariable("id") long companyId) {
        Company company = companyService.getById(companyId);
        return companyDtoConverter.toDto(company);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/company")
    public CompanyDto create(
            @RequestBody CompanyDto newCompany,
            @RequestParam("user-id") long userId
    ) {
        var company = companyDtoConverter.toDomain(newCompany);
        Company companySave = companyService.createCompany(company, userId);
        return companyDtoConverter.toDto(companySave);
    }

    @PutMapping("/company")
    public CompanyDto update(@RequestBody CompanyDto companyDto) {
        Company company = companyService.updateCompany(
                companyDto.getId(),
                companyDto.getName()
        );
        return companyDtoConverter.toDto(company);
    }

    @GetMapping("/company/{id}/order")
    public List<OrderDto> getCompanyOrders(@PathVariable("id") long companyId) {
        return companyService.getCompanyOrders(companyId)
                .stream()
                .map(orderDtoConverter::toDto)
                .toList();
    }

    @PostMapping("/company/{id}/order")
    public OrderDto createOrder(
            @PathVariable("id") long companyId,
            @RequestBody OrderDto orderDto
    ) {
        Order order = orderDtoConverter.toDomain(orderDto);
        Order orderSave = companyService.createOrder(companyId, order);
        return orderDtoConverter.toDto(orderSave);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/company/{cid}/order/{oid}")
    public void deleteOrder(
            @PathVariable("cid") long companyId,
            @PathVariable("oid") long orderId
    ) {
        companyService.deleteOrder(companyId, orderId);
    }
}
