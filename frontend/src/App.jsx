import GridDashboard from "./components/GridDashboard";

function App() {
  return (
    <div className="min-h-screen bg-gray-100 text-gray-900 p-4">
      <h1 className="text-3xl font-bold mb-4 text-center">Smart Grid AI Simulator</h1>
      <GridDashboard />
    </div>
  );
}

export default App;

// Setup Phase
// User selects a few regions (from a map or dropdown)
// User clicks to “add” generators, storage, or consumer nodes
// Display distances and projected losses between them visually

// Simulation Phase
// Simulation runs in real-time or step-by-step
// Dashboard shows:
    // Total generation vs. demand
    // Transmission losses
    // City power balance
    // AI forecast for next step

// Next, research what a realistic placement of grid nodes could look like and give users region "presets" to try out
// Let the user try once themselves, submit, and then let them click "optimal setup"