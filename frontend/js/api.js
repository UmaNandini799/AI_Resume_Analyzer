const API = (() => {
  const base = (window.APP_CONFIG?.API_BASE_URL || "http://localhost:8080/api").replace(/\/$/,"");
  const tokenKey = "resume_analyzer_token";
  const token = () => sessionStorage.getItem(tokenKey);
  async function request(path, options={}) {
    const headers = new Headers(options.headers || {});
    if (!(options.body instanceof FormData) && options.body !== undefined) headers.set("Content-Type","application/json");
    if (token()) headers.set("Authorization",`Bearer ${token()}`);
    let response;
    try { response = await fetch(base + path,{...options,headers}); }
    catch(e) { throw new Error("Cannot connect to the backend. Start the Spring Boot server and check js/config.js."); }
    const type = response.headers.get("content-type") || "";
    const data = type.includes("application/json") ? await response.json() : await response.text();
    if (!response.ok) throw new Error(data?.message || data?.error || `Request failed (${response.status})`);
    return data;
  }
  return {
    request, token, setToken:value=>sessionStorage.setItem(tokenKey,value),
    clearToken:()=>sessionStorage.removeItem(tokenKey),
    login:body=>request("/auth/login",{method:"POST",body:JSON.stringify(body)}),
    register:body=>request("/auth/register",{method:"POST",body:JSON.stringify(body)}),
    upload:file=>{const f=new FormData();f.append("file",file);return request("/resumes/upload",{method:"POST",body:f});},
    resumes:()=>request("/resumes"),
    resume:id=>request(`/resumes/${id}`),
    analyze:(id,jobDescriptionId)=>request(`/analysis/${id}`,{method:"POST",body:JSON.stringify({jobDescriptionId})}),
    analysis:id=>request(`/analysis/${id}`),
    jobs:()=>request("/jobs"),
    createJob:body=>request("/jobs",{method:"POST",body:JSON.stringify(body)}),
    applications:()=>request("/applications"),
    createApplication:body=>request("/applications",{method:"POST",body:JSON.stringify(body)}),
    updateApplication:(id,body)=>request(`/applications/${id}`,{method:"PUT",body:JSON.stringify(body)}),
    dashboard:()=>request("/dashboard/summary"),
    history:()=>request("/dashboard/recent-analyses"),
    profile:()=>request("/profile"),
    updateProfile:body=>request("/profile",{method:"PUT",body:JSON.stringify(body)}),
    suggestions:(path,body)=>request(path,{method:"POST",body:JSON.stringify(body)})
  };
})();
