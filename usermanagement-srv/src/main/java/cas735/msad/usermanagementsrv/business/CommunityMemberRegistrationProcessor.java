package cas735.msad.usermanagementsrv.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cas735.msad.usermanagementsrv.business.entities.Communitymember;
import cas735.msad.usermanagementsrv.dto.EmailRequest;
import cas735.msad.usermanagementsrv.dto.response.RoleCreationResponse;
import cas735.msad.usermanagementsrv.ports.CommunityMemberRegistrationService;
import cas735.msad.usermanagementsrv.ports.CommunityMemberRepository;
import cas735.msad.usermanagementsrv.ports.EmailService;

@Service
public class CommunityMemberRegistrationProcessor implements CommunityMemberRegistrationService{
    private final EmailService email;
    private final CommunityMemberRepository communityMemberRepository;

    @Autowired
    public CommunityMemberRegistrationProcessor(EmailService email, CommunityMemberRepository bikerRepository) {
        this.email = email;
        this.communityMemberRepository = bikerRepository;
    }

    @Override
    public RoleCreationResponse createCommunityMember(Communitymember communityMember) { 
        try {
            communityMemberRepository.save(communityMember);
            EmailRequest req = EmailRequest.builder()
                    .from("macdropadminoffice@mcmaster.ca")
                    .to(communityMember.getEtfemail()) 
                    .object("Community Member Registration on MacDrop")
                    .message("Community member registered successfully! " + "Your username is " + communityMember.getUsername())
                    .build();
            email.send(req);  
            return RoleCreationResponse.builder().statusCode(200).response("Created community member successfully!").build();
        } catch (Exception e) {
            return RoleCreationResponse.builder().statusCode(500).response("Failed to create community member successfully!").build();
        }

    }
    
}
