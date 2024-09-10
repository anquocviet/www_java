package fit.se.week01_lab_anquocviet_21080821.services;

import fit.se.week01_lab_anquocviet_21080821.entities.Role;
import fit.se.week01_lab_anquocviet_21080821.repositories.RoleRepository;

import java.util.Optional;
import java.util.Set;

/**
 * @description
 * @author: vie
 * @date: 9/9/24
 */
public class RoleService {
   private RoleRepository roleRepository;

   public RoleService() {
      roleRepository = new RoleRepository();
   }

   public Set<Role> findAll() {
      return roleRepository.findAll();
   }

   public Optional<Role> findById(String id) {
      return roleRepository.find(id);
   }

   public boolean addRole(Role role) {
      return roleRepository.add(role);
   }

   public boolean updateRole(Role role) {
      return roleRepository.update(role);
   }

   public boolean deleteRole(String roleId) {
      return roleRepository.delete(roleId);
   }
}
