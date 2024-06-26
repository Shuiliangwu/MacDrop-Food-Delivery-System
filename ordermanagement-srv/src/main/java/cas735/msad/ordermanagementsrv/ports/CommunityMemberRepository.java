package cas735.msad.ordermanagementsrv.ports;

import org.springframework.data.repository.CrudRepository;

import cas735.msad.ordermanagementsrv.business.entities.Communitymember;

public interface CommunityMemberRepository extends CrudRepository<Communitymember, Integer>{
    
}