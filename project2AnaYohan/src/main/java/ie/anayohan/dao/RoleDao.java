package ie.anayohan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.anayohan.domain.Role;

public interface RoleDao extends JpaRepository<Role, Integer>{

}
