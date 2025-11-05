import { useState, useEffect } from "react";
import { SimulationContext } from "../contexts/SimulationContext.jsx";

export function SimulationProvider({ children }) {
  const STARTING_BUDGET = 200;

  const [stateName, setStateName] = useState("");
  const [stateAbbr, setStateAbbr] = useState("");
  const [placements, setPlacements] = useState({});
  const [stateGeoJSON, setStateGeoJSON] = useState(null);
  const [simulationResults, setSimulationResults] = useState(null);

  // Undo history
  const [history, setHistory] = useState([]);

  function setState(stateObj) {
    if (!stateObj) {
      setStateName("");
      setStateAbbr("");
      setStateGeoJSON(null);
      return;
    }
    setStateName(stateObj.name);
    setStateAbbr(stateObj.abbr);
  }

  const calculateTotalCost = (placementsObj) =>
    Object.values(placementsObj)
      .flat()
      .reduce((sum, g) => sum + g.count * g.cost, 0);

  const handlePlaceGenerator = (county, type, count, cost) => {
    setHistory(h => [...h, JSON.parse(JSON.stringify(placements))]);
    setPlacements(prev => {
      const current = prev[county] ? [...prev[county]] : [];
      const existing = current.find(g => g.type === type);
      if (existing) existing.count += count;
      else current.push({ type, count, cost });
      return { ...prev, [county]: current };
    });
  };

  const handleRemoveGenerator = (county, type) => {
    setHistory(h => [...h, JSON.parse(JSON.stringify(placements))]);
    setPlacements(prev => {
      const current = prev[county] ? [...prev[county]] : [];
      const newCurrent = current.filter(g => g.type !== type);
      return { ...prev, [county]: newCurrent };
    });
  };

  const handleUndo = () => {
    if (history.length === 0) return;
    const prev = history[history.length - 1];
    setPlacements(prev);
    setHistory(h => h.slice(0, -1));
  };

  const handleReset = () => {
    setPlacements({});
    setHistory([]);
  };

  // Dynamic budget based on placements
  const budget = STARTING_BUDGET - calculateTotalCost(placements);

  return (
    <SimulationContext.Provider
      value={{
        stateName,
        stateAbbr,
        setState,
        placements,
        setPlacements,
        stateGeoJSON,
        setStateGeoJSON,
        simulationResults,
        setSimulationResults,
        history,
        budget,
        handlePlaceGenerator,
        handleRemoveGenerator,
        handleUndo,
        handleReset,
      }}
    >
      {children}
    </SimulationContext.Provider>
  );
}
