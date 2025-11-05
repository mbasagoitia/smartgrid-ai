import React, { useEffect, useContext } from "react";
import axios from "axios";
import Sidebar from "../components/Sidebar";
import CountyMap from "../components/CountyMap";
import { SimulationContext } from "../contexts/SimulationContext.jsx";
import "../styles/pages/SingleState.css";

export default function SingleState({ state, goBack }) {
  const {
    setState,
    stateGeoJSON,
    setStateGeoJSON,
    placements,
    handlePlaceGenerator,
    handleRemoveGenerator,
  } = useContext(SimulationContext);

  useEffect(() => {
    if (!state) return;
    setState(state);

    async function fetchCounties() {
      try {
        const resp = await axios.get(
          `${import.meta.env.VITE_API_URL}/profiles/counties?state=${state.abbr}`
        );
        const features = resp.data.features || [];
        const geoJson = {
          type: "FeatureCollection",
          features: features.map((f) => ({
            ...f,
            properties: {
              ...f.properties,
              centroidLat: f.properties?.centroidLat ?? 0,
              centroidLon: f.properties?.centroidLon ?? 0,
            },
          })),
        };
        setStateGeoJSON(geoJson);
      } catch (err) {
        console.error("Failed to fetch counties:", err);
        setStateGeoJSON({ type: "FeatureCollection", features: [] });
      }
    }

    fetchCounties();
  }, [state]);

  return (
    <div className="dashboard-container">
      <Sidebar state={state} goBack={goBack} />
      <div className="map-area">
        {stateGeoJSON && (
          <CountyMap
            geoJson={stateGeoJSON}
            placements={placements}
            onPlace={handlePlaceGenerator}
            onRemove={handleRemoveGenerator}
          />
        )}
      </div>
    </div>
  );
}
