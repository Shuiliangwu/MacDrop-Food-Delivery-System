package cas735.msad.accountingbillingsrv.ports;

import org.springframework.data.repository.CrudRepository;
import cas735.msad.accountingbillingsrv.business.entities.Communitymember;

public interface CommunityMemberRepository extends CrudRepository<Communitymember, Integer>{
}
