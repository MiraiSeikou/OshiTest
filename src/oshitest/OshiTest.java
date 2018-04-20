/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oshitest;

import java.util.Timer;
import java.util.TimerTask;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.FormatUtil;

/**
 *
 * @author joalima
 */
public class OshiTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        
        
        Timer t = new Timer();
        System.out.println("Ram\t\t\tDisk\t\tCPU");
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                String totalMemory = FormatUtil.formatBytes(hal.getMemory().getTotal());
                String availableMemory = FormatUtil.formatBytes(hal.getMemory().getAvailable());

                long totalDisk = 0;
                long availableDisk = 0;

                for (HWDiskStore ds : hal.getDiskStores()) {
                    totalDisk += ds.getSize();
                    for (HWPartition partition : ds.getPartitions()) {
                        availableDisk = partition.getSize();
                    }
                }

                String aDisk = FormatUtil.formatBytes(availableDisk);
                String tDisk = FormatUtil.formatBytes(totalDisk);
                double CpuLoad = hal.getProcessor().getSystemCpuLoad()*100;
                System.out.format(
                        "%s/%s | %s/%s | %.2f%%\n", 
                        availableMemory, totalMemory, 
                        aDisk, tDisk, CpuLoad);
            }
        }, 0, 5000);
        
    }
    
}
