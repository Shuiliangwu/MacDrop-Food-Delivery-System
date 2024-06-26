package cas735.msad.adminmanagementsrv.ports;

import cas735.msad.adminmanagementsrv.business.entities.Biker;
import cas735.msad.adminmanagementsrv.business.entities.Location;

import java.util.List;
import java.util.Map;

public interface MonitoringService {

//    Biker Monitoring
    Biker getBiker(int id);
    List<Biker> getAllBikers();
    Map<Integer, Integer> getCurrentBikerLoads();
    Map<Integer, Integer> getHistoryBikerLoads();

//    Location Monitoring
    Location getLocation(int id);
    List<Location> getAllLocations();
    Map<Integer, Integer> getLocationCurrentLoads();
    Map<Integer, Integer> getLocationHistoryLoads();


}