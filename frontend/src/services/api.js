export async function fetchCounties(state) {
    const res = await fetch(`/api/counties?state=${state}`);
    if (!res.ok) throw new Error("Failed to fetch counties");
    return await res.json();
}

export async function fetchCountyProfiles(state) {
    const res = await fetch(`/api/profiles/counties?state=${state}`);
    if (!res.ok) throw new Error("Failed to fetch county profiles");
    return res.json();
  }
