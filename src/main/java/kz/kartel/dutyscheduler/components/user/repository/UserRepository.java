package kz.kartel.dutyscheduler.components.user.repository;

import kz.kartel.dutyscheduler.components.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("select u from User u where u.id = :userId")
    public User getUserById(@Param("userId") Long userId);

    @Query(value="select u from User u where LOWER(u.email) = :email")
    public User getUserByEmail(@Param("email") String email);

    @Query(value = "select user_id from users order by user_id desc limit 1", nativeQuery = true)
    public Object getLastIUserId();

    @Modifying
    @Transactional
    @Query(value="insert into users (user_id, firstname, lastname, email, title, phone, department, address) values (:userId, :firstName, :lastName, :email, :title, :phone, :department, :address)", nativeQuery = true)
    public void saveUser(@Param("userId") Integer userId, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email, @Param("title") String title, @Param("phone") String phone, @Param("department") String department, @Param("address") String address);

    @Modifying
    @Transactional
    @Query(value = "update User set firstName = :firstName, lastName = :lastName, title = :title, phone = :phone, department = :department, address = :address where email = :email")
    void updateUser(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("title") String title, @Param("phone") String phone, @Param("department") String department, @Param("address") String address, @Param("email") String email);
}
