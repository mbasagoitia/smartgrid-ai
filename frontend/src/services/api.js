export async function fetchCountyProfiles(state) {
    const res = await fetch(`/api/profiles/counties?state=${state}`);
    if (!res.ok) throw new Error("Failed to fetch county profiles");
    return res.json();
  }
