package cas735.msad.adminmanagementsrv.business;

import cas735.msad.adminmanagementsrv.business.entities.Biker;
import cas735.msad.adminmanagementsrv.business.entities.Location;
import cas735.msad.adminmanagementsrv.ports.BikerMonitoringRepository;
import cas735.msad.adminmanagementsrv.ports.LocationMonitoringRepository;
import cas735.msad.adminmanagementsrv.ports.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MonitoringProcessor implements MonitoringService {


    private final LocationMonitoringRepository locationMonitoringRepository;
    private final BikerMonitoringRepository bikerMonitoringRepository;


    @Autowired
    public MonitoringProcessor(LocationMonitoringRepository locationMonitoringRepository, BikerMonitoringRepository bikerMonitoringRepository) {
        this.locationMonitoringRepository = locationMonitoringRepository;
        this.bikerMonitoringRepository = bikerMonitoringRepository;
    }

    @Override
    public Biker getBiker(int id) {
        Optional<Biker> tempBiker = bikerMonitoringRepository.findById(id);

        if (!tempBiker.isPresent()){
            throw new RuntimeException("Biker with id " + id + " not found");
        }
        return tempBiker.get();
    }

    @Override
    public List<Biker> getAllBikers() {
        return bikerMonitoringRepository.findAll();
    }

    @Override
    public Map<Integer, Integer> getCurrentBikerLoads() {
        Map<Integer, Integer> bikerloads = new HashMap<>();
        List<Biker> bikers = bikerMonitoringRepository.findAll();
        for (Biker b:bikers) {
            bikerloads.put(b.getBikerid(), b.getCurrentworkload());
        }
        return bikerloads;
    }

    @Override
    public Map<Integer, Integer> getHistoryBikerLoads() {
        Map<Integer, Integer> bikerloads = new HashMap<>();
        List<Biker> bikers = bikerMonitoringRepository.findAll();
        for (Biker b:bikers) {
            bikerloads.put(b.getBikerid(), b.getHistoryworkload());
        }
        return bikerloads;
    }

    @Override
    public Location getLocation(int id) {
        Optional<Location> tempLocation = locationMonitoringRepository.findById(id);

        if (!tempLocation.isPresent()){
            throw new RuntimeException("Location with id " + id + " not found");
        }
        return tempLocation.get();
    }

    @Override
    public List<Location> getAllLocations() {
        return locationMonitoringRepository.findAll();
    }

    @Override
    public Map<Integer, Integer> getLocationCurrentLoads() {
        Map<Integer, Integer> locationloads = new HashMap<>();
        List<Location> locations = locationMonitoringRepository.findAll();
        for (Location l:locations) {
            locationloads.put(l.getLocationid(), l.getCurrentworkload());
        }
        return locationloads;
    }

    @Override
    public Map<Integer, Integer> getLocationHistoryLoads() {
        Map<Integer, Integer> locationloads = new HashMap<>();
        List<Location> locations = locationMonitoringRepository.findAll();
        for (Location l:locations) {
            locationloads.put(l.getLocationid(), l.getHistoryworkload());
        }
        return locationloads;
    }
}
