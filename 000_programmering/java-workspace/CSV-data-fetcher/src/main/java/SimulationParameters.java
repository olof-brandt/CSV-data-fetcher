package sunnyOffgrid02;

import sunnyOffgrid02.SimulationParameters_210517;

public class SimulationParameters
{
   SimulationParameters_210517 simPar = new SimulationParameters_210517();

   public String orderDate = simPar.orderDate;
   public String customerName = simPar.customerName;
   public String emailAdress = simPar.customerName;
   public String location = simPar.location;
   public double latitude = simPar.latitude;
   public double longitude = simPar.longitude;
   public int energyConsumtionWh = simPar.energyConsumtionWh;
   public int firstUsageMonth = simPar.firstUsageMonth;
   public int lastUsageMonth = simPar.lastUsageMonth;
   public int nRainyDays = simPar.nRainyDays;
   public int nSunnyDays = simPar.nSunnyDays;
   public boolean lithium = simPar.lithium;
   public int energyConsumtionJ = simPar.energyConsumtionJ;
}
