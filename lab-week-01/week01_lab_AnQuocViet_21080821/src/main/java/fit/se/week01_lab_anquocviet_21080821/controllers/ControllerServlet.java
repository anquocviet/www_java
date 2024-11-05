package fit.se.week01_lab_anquocviet_21080821.controllers;

import fit.se.week01_lab_anquocviet_21080821.entities.Account;
import fit.se.week01_lab_anquocviet_21080821.entities.Role;
import fit.se.week01_lab_anquocviet_21080821.services.AccountService;
import fit.se.week01_lab_anquocviet_21080821.services.LogService;
import fit.se.week01_lab_anquocviet_21080821.services.RoleService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * @description
 * @author: vie
 * @date: 7/9/24
 */
@WebServlet(name = "ControllerServlet", value = "/controller")
public class ControllerServlet extends HttpServlet {
   @Inject
   private AccountService accountService;
   @Inject
   private RoleService roleService;
   @Inject
   LogService logService;
   private Logger logger;

   @Override
   public void init() throws ServletException {
      logger = Logger.getLogger(ControllerServlet.class.getName());
      super.init();
   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String action = req.getParameter("action");
      HttpSession session = req.getSession();
      switch (action.toLowerCase()) {
         // Account
         case "login" -> {
            String phone = req.getParameter("phone");
            String password = req.getParameter("password");
            Account account = accountService.checkLogin(phone, password);
            if (account != null) {
               logService.addLog(account.getAccountId());
               session.setAttribute("account", account);
               boolean isAdmin =
                     account
                           .getGrantAccesses()
                           .stream()
                           .anyMatch(grantAccess ->
                                       grantAccess
                                             .getRole()
                                             .getRoleName()
                                             .equalsIgnoreCase("administrator"));
               if (isAdmin) {
                  session.setAttribute("accounts", accountService.findAll());
                  session.setAttribute("roles", roleService.findAll());
               }
               resp.sendRedirect("dashboard.jsp");
            } else {
               req.setAttribute("message", "Login failed");
               req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
         }
         case "logout" -> {
            Account account = (Account) session.getAttribute("account");
            logService.updateLog(account.getAccountId());
            session.removeAttribute("account");
            session.removeAttribute("accounts");
            resp.sendRedirect("index.jsp");
         }
         case "redirect" -> {
            String page = req.getParameter("page");
            if (page.equalsIgnoreCase("account-of-role.jsp")) {
               String role = req.getParameter("role");
               Set<Account> accounts = accountService.findByRole(role);
               req.setAttribute("accounts", accounts);
               req.getRequestDispatcher("/account-of-role.jsp").forward(req, resp);

            } else if (page.equalsIgnoreCase("role.jsp")) {
               String roleId = req.getParameter("role_id");
               Role role = roleService.findById(roleId).orElse(null);
               req.setAttribute("role", role);
               req.getRequestDispatcher("/role.jsp").forward(req, resp);
            } else if (page.equalsIgnoreCase("account.jsp")) {
               String accountId = req.getParameter("account_id");
               Account account = accountService.findById(accountId).orElse(null);
               Set<Role> roles = roleService.findAll();
               req.setAttribute("account", account);
               req.setAttribute("roles", roles);
               req.getRequestDispatcher("/account.jsp").forward(req, resp);
            } else {
               req.getRequestDispatcher("/page-not-found.jsp").forward(req, resp);
            }
         }
         default -> {
            req.getRequestDispatcher("/page-not-found.jsp").forward(req, resp);
         }
      }
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String action = req.getParameter("action");
      HttpSession session = req.getSession();
      switch (action) {
         // Account
         case "add-account" -> {
            String fullName = req.getParameter("full_name");
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String status = req.getParameter("status");
            String[] roles = req.getParameterValues("role");

            Account account = new Account();
            account.setAccountId(UUID.randomUUID().toString());
            account.setFullName(fullName);
            account.setPassword(password);
            account.setEmail(email);
            account.setPhone(phone);
            account.setStatus(Byte.parseByte(status));

            boolean isAdded = accountService.add(account, roles);
            if (isAdded) {
               session.setAttribute("accounts", accountService.findAll());
               resp.sendRedirect("dashboard.jsp");
            } else {
               req.setAttribute("message", "Failed to add account");
               req.getRequestDispatcher("/account.jsp").forward(req, resp);
            }

         }
         case "update-account" -> {
            String accountId = req.getParameter("account_id");
            String fullName = req.getParameter("full_name");
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String status = req.getParameter("status");
            String[] roles = req.getParameterValues("role");

            Account account = new Account();
            account.setAccountId(accountId);
            account.setFullName(fullName);
            account.setPassword(password);
            account.setEmail(email);
            account.setPhone(phone);
            account.setStatus(Byte.parseByte(status));

            boolean isUpdated = accountService.update(account, roles == null ? new String[]{} : roles);
            if (isUpdated) {
               session.setAttribute("accounts", accountService.findAll());
               resp.sendRedirect("dashboard.jsp");
            } else {
               req.setAttribute("message", "Failed to update account");
               req.getRequestDispatcher("/account.jsp").forward(req, resp);
            }
         }
         case "delete-account" -> {
            String accountId = req.getParameter("account_id");
            boolean isDeleted = accountService.delete(accountId);
            if (isDeleted) {
               session.setAttribute("accounts", accountService.findAll());
               resp.sendRedirect("dashboard.jsp");
            } else {
               req.setAttribute("message", "Failed to delete account");
               req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
            }
         }

         // Role
         case "add-role" -> {
            String roleId = req.getParameter("role_id");
            String roleName = req.getParameter("role_name");
            String description = req.getParameter("description");
            String status = req.getParameter("status");

            Role role = new Role();
            role.setRoleId(roleId.isEmpty() ? UUID.randomUUID().toString() : roleId);
            role.setRoleName(roleName);
            role.setDescription(description);
            role.setStatus(Byte.parseByte(status));

            boolean isAdded = roleService.addRole(role);
            if (isAdded) {
               session.setAttribute("roles", roleService.findAll());
               resp.sendRedirect("dashboard.jsp");
            } else {
               req.setAttribute("message", "Failed to add role");
               req.getRequestDispatcher("/role.jsp").forward(req, resp);
            }
         }
         case "update-role" -> {
            String roleId = req.getParameter("role_id");
            String roleName = req.getParameter("role_name");
            String description = req.getParameter("description");
            String status = req.getParameter("status");

            Role role = new Role();
            role.setRoleId(roleId);
            role.setRoleName(roleName);
            role.setDescription(description);
            role.setStatus(Byte.parseByte(status));
            boolean isUpdated = roleService.updateRole(role);

            if (isUpdated) {
               session.setAttribute("roles", roleService.findAll());
               resp.sendRedirect("dashboard.jsp");
            } else {
               req.setAttribute("message", "Failed to update role");
               req.getRequestDispatcher("/role.jsp").forward(req, resp);
            }
         }
         case "delete-role" -> {
            String roleId = req.getParameter("role_id");
            boolean isDeleted = roleService.deleteRole(roleId);
            if (isDeleted) {
               session.setAttribute("roles", roleService.findAll());
               resp.sendRedirect("dashboard.jsp");
            } else {
               req.setAttribute("message", "Failed to delete role");
               req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
            }
         }

         default -> {
            req.getRequestDispatcher("/page-not-found.jsp").forward(req, resp);
         }
      }
   }
}
