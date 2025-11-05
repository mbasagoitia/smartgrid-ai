import React, { useContext } from "react";
import Dashboard from "./Dashboard";
import { SimulationContext } from "../contexts/SimulationContext.jsx";
import "../styles/components/Sidebar.css";

export default function Sidebar({ state, goBack }) {
  const { simulationResults } = useContext(SimulationContext);

  return (
    // If simulation has run, show simulationResults, else show this
    <div className="sidebar border-l shadow-inner flex flex-col p-4 overflow-y-auto">
      <h1>SmartGrid Simulator</h1>
      <div className="sidebar-content">
        <Dashboard goBack={goBack} />
      </div>
    </div>
  );
}
