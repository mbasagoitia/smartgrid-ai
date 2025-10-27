
import React, {useState} from 'react';
import axios from 'axios';

function App() {
  const [state, setState] = useState('Colorado');
  const [strategy, setStrategy] = useState('Conservative');
  const [result, setResult] = useState(null);

  const run = async () => {
    const payload = {
      state: state,
      placements: [
        {type: 'solar', county: 'RegionA', count: 3},
        {type: 'wind', county: 'RegionB', count: 1}
      ],
      battery_strategy: strategy
    };
    const resp = await axios.post(`${import.meta.env.VITE_API_URL}/simulate`, payload);
    setResult(resp.data);
  };

  return (
    <div style={{padding:20}}>
      <h1>SmartGrid Simulator (scaffold)</h1>
      <div>
        <label>State: </label>
        <select value={state} onChange={e=>setState(e.target.value)}>
          <option>Colorado</option>
          <option>California</option>
          <option>Texas</option>
        </select>
      </div>
      <div>
        <label>Battery Strategy: </label>
        <select value={strategy} onChange={e=>setStrategy(e.target.value)}>
          <option>Conservative</option>
          <option>Aggressive</option>
          <option>Predictive</option>
        </select>
      </div>
      <button onClick={run}>Run Simulation</button>
      <pre>{result && JSON.stringify(result, null, 2)}</pre>
    </div>
  );
}

export default App;
