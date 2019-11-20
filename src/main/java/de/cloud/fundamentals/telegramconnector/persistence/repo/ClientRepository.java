package de.cloud.fundamentals.telegramconnector.persistence.repo;

import de.cloud.fundamentals.telegramconnector.persistence.domain.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

}
