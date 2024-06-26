package cas735.msad.usermanagementsrv.ports;

import org.springframework.data.repository.CrudRepository;

import cas735.msad.usermanagementsrv.business.entities.Communitymember;

public interface CommunityMemberRepository extends CrudRepository<Communitymember, Integer>{
    
}
