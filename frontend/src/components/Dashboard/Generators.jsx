import { GiWindTurbine } from "react-icons/gi";
import { FaSolarPanel, FaWater } from "react-icons/fa";
import { PiNuclearPlantDuotone } from "react-icons/pi";

const GENERATORS = [
  { type: 'Solar', cost: 10, icon: <FaSolarPanel /> },
  { type: 'Wind', cost: 15, icon: <GiWindTurbine /> },
  { type: 'Hydro', cost: 30, icon: <FaWater /> },
  { type: 'Nuclear', cost: 50, icon: <PiNuclearPlantDuotone /> }
];

export default GENERATORS;