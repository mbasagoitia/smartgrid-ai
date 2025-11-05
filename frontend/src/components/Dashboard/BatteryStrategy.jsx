export default function BatteryStrategy() {
    return (
        <div>
            <h3 className="font-semibold mb-4">Battery Strategy</h3>
            <label htmlFor="battery-strategy-select" className="d-block text-sm mb-2">
            Select strategy:
            </label>
            <select
            id="battery-strategy-select"
            value={strategy}
            onChange={(e) => setStrategy(e.target.value)}
            className="border rounded px-2 py-1 w-full"
            >
                <option>Conservative</option>
                <option>Aggressive</option>
                <option>Predictive</option>
            </select>
        </div>
    )
}