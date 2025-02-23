package com.final_project.ua_team_final_project.services;

import com.final_project.ua_team_final_project.models.*;
import com.final_project.ua_team_final_project.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class PageDataManager {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;

    private final AvailableProductsRepository availableProductsRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierOrderRepository supplierOrderRepository;

    @Autowired
    private EntityManager entityManager;

    public PageDataManager(UserRepository userRepository, DepartmentRepository departmentRepository,
                           RoleRepository roleRepository, OrderRepository orderRepository, OrderStatusRepository orderStatusRepository,
                           AvailableProductsRepository availableProductsRepository, CategoryRepository categoryRepository,
                           SupplierRepository supplierRepository, SupplierOrderRepository supplierOrderRepository) {


        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;

        this.availableProductsRepository = availableProductsRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.supplierOrderRepository = supplierOrderRepository;

    }

    public void setAdminModel(Model model, Integer urlPageNumber, Integer pageSize, String order, User user) {

        int pageNumber = urlPageNumber - 1;

        Sort.Direction direction = Sort.Direction.ASC;

        if (order.endsWith("desc")) {
            direction = Sort.Direction.DESC;
            order = order.substring(0, order.length() - 5);
        }
        Sort sort = Sort.by(direction, order);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = null;
        try {
            page = userRepository.findAll(pageable);
        } catch (PropertyReferenceException e) {
            setAdminModel(model, 1, 10, "userId", user);
        }
        if (page != null) {
            model.addAttribute("user", user);
            model.addAttribute("users", page.getContent());
            model.addAttribute("pageNumber", urlPageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("order", sort.toString());
        }
    }

    public void setEditUserModel(Long id, Model model, User user) {
        model.addAttribute("user", user);
        model.addAttribute("edituser", userRepository.findById(id).orElse(null));
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
    }

    public void setFincoModel(Model model, Integer urlPageNumber, Integer pageSize, String order, User user) {

        int pageNumber = urlPageNumber - 1;

        Sort.Direction direction = Sort.Direction.ASC;

        if (order.endsWith("desc")) {
            direction = Sort.Direction.DESC;
            order = order.substring(0, order.length() - 5);
        }
        Sort sort = Sort.by(direction, order);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> page = null;
        try {
            page = orderRepository.findByApprovedByHeadAndApprovedByFinDeptAndStatusNot(true,
                    false, orderStatusRepository.findById(4L).orElse(null), pageable);
        } catch (PropertyReferenceException e) {
            setFincoModel(model, 1, 10, "orderId", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("pageNumber", urlPageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("orders", page.getContent());
        model.addAttribute("order", sort.toString());
    }

    public void setSupplierModel(Model model, User user) {
        model.addAttribute("user", user);
        List<Order> internalOrders = orderRepository.findByApprovedByHeadTrueAndApprovedByFinDeptTrueAndStatusStatusId(2L);
        model.addAttribute("internalOrders", internalOrders);
        model.addAttribute("department", user.getDepartment().getName());
    }


    public void getAvailableProductsModel(Model model, Integer urlPageNumber, Integer pageSize, String order, User user, Category category, Supplier supplier) {
        int pageNumber = urlPageNumber - 1;
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.endsWith("desc")) {
            direction = Sort.Direction.DESC;
            order = order.substring(0, order.length() - 5);
        }
        Sort sort = Sort.by(direction, order);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<AvailableProducts> page = null;
        Specification<AvailableProducts> spec = Specification.where(null);

        if (category != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category").get("categoryId"), category.getCategoryId()));
        }

        if (supplier != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("supplier").get("supplierId"), supplier.getSupplierId()));
        }
        try {
            page = availableProductsRepository.findAll(pageable);
        } catch (PropertyReferenceException e) {
            getAvailableProductsModel(model, 1, 10, "productCode", user, category, supplier);
        }
        model.addAttribute("user", user);
        model.addAttribute("pageNumber", urlPageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("order", sort.toString());
        model.addAttribute("availableProducts", page.getContent());

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("suppliers", supplierRepository.findAll());
        model.addAttribute("category", category);
        model.addAttribute("supplier", supplier);
    }

    public void setNewUserModel(Model model, User user) {
        model.addAttribute("user", user);
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());

    }

    public List<AvailableProducts> findByCategoryAndSupplier(Long categoryId, Long supplierId, int pageSize, String order) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AvailableProducts> query = cb.createQuery(AvailableProducts.class);
        Root<AvailableProducts> product = query.from(AvailableProducts.class);

        List<Predicate> predicates = new ArrayList<>();

        if (categoryId != null) {
            predicates.add(cb.equal(product.get("category").get("categoryId"), categoryId));
        }

        if (supplierId != null) {
            predicates.add(cb.equal(product.get("supplier").get("supplierId"), supplierId));
        }

        query.where(predicates.toArray(new Predicate[0]));


        if (order != null) {
            String[] orderParts = order.split(":");
            String field = orderParts[0].trim();
            String direction = orderParts.length > 1 ? orderParts[1].trim() : "ASC";

            if (direction.equalsIgnoreCase("ASC")) {
                query.orderBy(cb.asc(product.get(field)));
            } else {
                query.orderBy(cb.desc(product.get(field)));
            }
        }

        return entityManager.createQuery(query)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public void setHistoryModel(Model model, User user, Integer urlPageNumber, Integer pageSize, String order) {
        int pageNumber = urlPageNumber - 1;

        Sort.Direction direction = Sort.Direction.ASC;

        if (order.endsWith("desc")) {
            direction = Sort.Direction.DESC;
            order = order.substring(0, order.length() - 5);
        }
        Sort sort = Sort.by(direction, order);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> page = null;
        String role = user.getRole().getName();
        try {
            page = switch (role) {
                case "USER" ->
                        orderRepository.findByUserAndStatusNot(user, orderStatusRepository.findById(1L).get(), pageable);
                case "HEAD" -> orderRepository.findByDept(user.getDepartment(), pageable);
                case "FINCO" -> orderRepository.findByStatusIn(List.of(orderStatusRepository.findById(2L).get(),
                        orderStatusRepository.findById(4L).get()), pageable);
                case "SUPPLIER" -> orderRepository.findByUserAndStatusNot(user, orderStatusRepository.findById(3L).get(), pageable);
                default -> throw new IllegalStateException("Unexpected value: " + role);
            };
        } catch (PropertyReferenceException _) {
            setHistoryModel(model, user, 1, 10, "orderId");
        }
        model.addAttribute("user", user);
        model.addAttribute("pageNumber", urlPageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("order", sort.toString());
        model.addAttribute("orders", page.getContent());

    }

    public void setSupplierHistoryModel(Model model, User user, Integer urlPageNumber, Integer pageSize, String order) {
        int pageNumber = urlPageNumber - 1;

        Sort.Direction direction = Sort.Direction.ASC;
        if (order.endsWith("desc")) {
            direction = Sort.Direction.DESC;
            order = order.substring(0, order.length() - 5);
        }
        Sort sort = Sort.by(direction, order);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<SupplierOrder> page = supplierOrderRepository.findAll(pageable);

        model.addAttribute("department", user.getDepartment().getName());
        model.addAttribute("user", user);
        model.addAttribute("pageNumber", urlPageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("order", sort.toString());
        model.addAttribute("supplierOrders", page.getContent());
    }
}