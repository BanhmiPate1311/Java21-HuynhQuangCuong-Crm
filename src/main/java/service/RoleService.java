package service;

import model.RoleModel;
import repository.RoleRepository;

public class RoleService {

    private RoleRepository roleRepository = new RoleRepository();

    public boolean inserRole(String name, String description) {
        return roleRepository.insertRole(name, description);
    }

    public boolean deleteRole(int id) {
        return roleRepository.deleteById(id);
    }

    public RoleModel findById(int id){
        return roleRepository.findById(id);
    }

    public boolean updateRole(int id, String name, String description) {
        return roleRepository.updateRole(id, name, description);
    }
}
