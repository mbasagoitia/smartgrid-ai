import { useEffect, useState } from "react";
import { getGridState, stepSimulation } from "../services/api";
import ChartView from "./ChartView";

export default function GridDashboard() {
  const [grid, setGrid] = useState(null);

  const refreshState = async () => {
    const data = await getGridState();
    setGrid(data);
  };

  useEffect(() => { refreshState(); }, []);

  const handleStep = async () => {
    const data = await stepSimulation();
    setGrid(data);
  };

  return (
    <div className="p-4 bg-white rounded-2xl shadow-md">
      {grid ? (
        <>
          <ChartView grid={grid} />
          <button onClick={handleStep} className="bg-blue-500 text-white px-4 py-2 rounded-md mt-4">
            Step Simulation
          </button>
        </>
      ) : <p>Loading...</p>}
    </div>
  );
}
