import React, { useState, useContext } from "react";
import axios from "axios";
import BudgetBar from "./Dashboard/BudgetBar";
import GeneratorPalette from "./Dashboard/GeneratorPalette";
import { SimulationContext } from "../contexts/SimulationContext.jsx";
import "../styles/components/Dashboard.css";
import Instructions from "./Dashboard/Instructions.jsx";
import ManageGenerators from "./Dashboard/ManageGenerators.jsx";
import BatteryStrategy from "./Dashboard/BatteryStrategy.jsx";

export default function Dashboard({ goBack }) {
  const {
    placements,
    budget,
    stateName,
    stateAbbr,
    setSimulationResults,
    handlePlaceGenerator,
    handleRemoveGenerator,
    handleUndo,
    handleReset,
    history,
  } = useContext(SimulationContext);

  const [strategy, setStrategy] = useState("Conservative");

  const runSimulation = async () => {
    if (!stateAbbr) {
      alert("Please select a state first.");
      return;
    }

    const payload = {
      state: { name: stateName, abbr: stateAbbr },
      placements: Object.entries(placements).flatMap(([geoid, gens]) =>
        gens
          .filter((g) => g.type !== "battery")
          .map((g) => ({ county: geoid, type: g.type, count: g.count }))
      ),
      battery_strategy: strategy,
    };

    try {
      const resp = await axios.post(`${import.meta.env.VITE_API_URL}/simulate`, payload);
      setSimulationResults(resp.data);
    } catch (err) {
      console.error("Simulation failed:", err);
    }
  };

  return (
    <div>
      <h2 className="font-semibold mb-2">State: {stateName}</h2>
      {goBack && (
        <p onClick={goBack} className="choose-state-text mb-3">
          Choose Another State
        </p>
      )}

      <div className="my-4">
        <Instructions />
      </div>

      <BudgetBar budget={budget} />

      <h3 className="font-semibold my-4">Power Generators</h3>
      <ManageGenerators
        onReset={handleReset}
        onUndo={handleUndo}
        disableUndo={history.length === 0}
      />

      <div className="shadow">
        <GeneratorPalette onPlace={handlePlaceGenerator} budget={budget} />
      </div>

      <div className="my-4">
        <BatteryStrategy />
      </div>

      <div className="d-flex justify-content-center">
        <button
          className="mt-2 bg-blue-500 px-4 py-2 rounded w-full m-auto"
          onClick={runSimulation}
        >
          Start Simulation
        </button>
      </div>
    </div>
  );
}
