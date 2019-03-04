package capgemini.stage.model.charity;

import org.springframework.data.jpa.repository.JpaRepository;

import capgemini.stage.model.charity.Charity;

public interface ICharityRepository extends JpaRepository<Charity, Long> {

}