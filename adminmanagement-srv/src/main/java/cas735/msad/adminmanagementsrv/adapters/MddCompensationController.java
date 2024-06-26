package cas735.msad.adminmanagementsrv.adapters;

import cas735.msad.adminmanagementsrv.MddCompensationQueueConfiguration;
import cas735.msad.adminmanagementsrv.dto.MddCompensation;
import cas735.msad.adminmanagementsrv.ports.ApprovalsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class MddCompensationController {

    public final ApprovalsService approvalsService;

    public MddCompensationController(ApprovalsService approvalsService) {
        this.approvalsService = approvalsService;
    }

    // receive & approve weekly MDD compensation invoice from book store

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MddCompensationQueueConfiguration.QUEUE_NAME_MDDCOMPENSATION, durable = "true"),
            exchange = @Exchange(
                    value = MddCompensationQueueConfiguration.EXCHANGE_NAME_MDDCOMPENSATION, ignoreDeclarationExceptions = "true"),
            key = MddCompensationQueueConfiguration.ROUTING_MDDCOMPENSATION))
    public void receive(MddCompensation req) {
        try {
            log.info("Reading message: '" + req + "'");
            approvalsService.approveWeeklyBookStoreMddCompensationInvoice(req);
        } catch (Exception e) {
            log.info("Error receiving message from OrderManagementService");
            log.info(e.toString());
        }
    }
}
