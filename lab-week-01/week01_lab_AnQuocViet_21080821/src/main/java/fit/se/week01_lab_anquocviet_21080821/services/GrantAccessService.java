package fit.se.week01_lab_anquocviet_21080821.services;

import fit.se.week01_lab_anquocviet_21080821.entities.GrantAccess;
import fit.se.week01_lab_anquocviet_21080821.repositories.GrantAccessRepository;

import java.util.Optional;
import java.util.Set;

/**
 * @description
 * @author: vie
 * @date: 10/9/24
 */
public class GrantAccessService {
   private GrantAccessRepository grantAccessRepository;

   public GrantAccessService() {
      grantAccessRepository = new GrantAccessRepository();
   }

   public boolean add(GrantAccess grantAccess) {
      return grantAccessRepository.add(grantAccess);
   }

   public boolean update(GrantAccess grantAccess) {
      return grantAccessRepository.update(grantAccess);
   }

   public boolean delete(GrantAccess grantAccess) {
      return grantAccessRepository.delete(grantAccess);
   }

   public Optional<GrantAccess> findByAccountAndRole(String accountId, String roleId) {
      return grantAccessRepository.find(accountId, roleId);
   }

   public Set<GrantAccess> findByAccount(String accountId) {
      return grantAccessRepository.findByAccount(accountId);
   }
}
