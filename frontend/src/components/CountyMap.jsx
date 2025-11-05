import { useState, useEffect, useRef } from "react";
import Map, { Source, Layer, Marker } from "react-map-gl";
import mapboxgl from "mapbox-gl";
import { useDrop } from "react-dnd";
import GENERATORS from "./Dashboard/Generators";
import "../styles/components/CountyMap.css";

export default function CountyMap({ geoJson, placements = {}, onPlace, onRemove }) {
  const [viewState, setViewState] = useState({ longitude: -105.5, latitude: 39.0, zoom: 6 });
  const [mapLoaded, setMapLoaded] = useState(false);
  const mapRef = useRef(null);

  // Fit map bounds to counties
  useEffect(() => {
    if (!mapLoaded || !geoJson?.features?.length) return;
    const map = mapRef.current.getMap();
    const bounds = new mapboxgl.LngLatBounds();
    geoJson.features.forEach(f => {
      const coords = f.geometry?.coordinates;
      if (!coords) return;
      const addCoords = c => Array.isArray(c[0]) ? c.forEach(addCoords) : bounds.extend([c[0], c[1]]);
      addCoords(coords);
    });
    if (!bounds.isEmpty()) map.fitBounds(bounds, { padding: 40, duration: 800 });
  }, [geoJson, mapLoaded]);

  // Drag-and-drop generators
  const [, dropRef] = useDrop({
    accept: "GENERATOR",
    drop: (item, monitor) => {
      const map = mapRef.current?.getMap();
      if (!map) return;

      const offset = monitor.getClientOffset();
      if (!offset) return;

      const rect = map.getContainer().getBoundingClientRect();
      const point = map.unproject([offset.x - rect.left, offset.y - rect.top]);

      const features = map.queryRenderedFeatures(map.project([point.lng, point.lat]), { layers: ["county-fills"] });
      if (!features.length) return;

      const geoid = features[0].properties.geoid;
      if (!geoid) return;

      onPlace(geoid, item.type, 1, item.cost);
    },
  });

  // Markers to represent generator placements
  const markers = Object.entries(placements).flatMap(([geoid, gens]) =>
    gens.map((g, i) => {
      const f = geoJson.features.find(ft => ft.properties.geoid === geoid);
      if (!f?.properties) return null;

      const lon = f.properties.centroidLon ?? f.properties.centroid_lon;
      const lat = f.properties.centroidLat ?? f.properties.centroid_lat;
      if (lat == null || lon == null) return null;

      return { longitude: lon + 0.05 * i, latitude: lat, type: g.type, count: g.count, geoid };
    }).filter(Boolean)
  );

  return (
    <div ref={dropRef} className="county-map-container">
      <Map
        {...viewState}
        onMove={evt => setViewState(evt.viewState)}
        mapStyle="mapbox://styles/mapbox/light-v11"
        mapboxAccessToken={import.meta.env.VITE_MAPBOX_TOKEN}
        ref={mapRef}
        className="county-map"
        onLoad={() => setMapLoaded(true)}
      >
        {geoJson?.features?.length > 0 && (
          <Source id="counties" type="geojson" data={geoJson}>
            <Layer id="county-fills" type="fill" paint={{ "fill-color": "#74a9cf", "fill-opacity": 0.4 }} />
            <Layer id="county-borders" type="line" paint={{ "line-color": "#045a8d", "line-width": 1 }} />
          </Source>
        )}

        {markers.map((m, idx) => (
          <Marker key={`${m.geoid}-${idx}`} longitude={m.longitude} latitude={m.latitude} anchor="bottom">
            <div
              className={`marker marker-${m.type}`}
              title={`${m.type} x${m.count}`}
              onClick={() => onRemove(m.geoid, m.type)}
            >
              {GENERATORS.find(g => g.type === m.type)?.icon}
            </div>
          </Marker>
        ))}
      </Map>
    </div>
  );
}
