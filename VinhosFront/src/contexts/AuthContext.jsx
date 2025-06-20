import { createContext, useState } from "react";
import api from "../services/api";

export const AuthCtx = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);

  async function login(email, senha) {
    const { data } = await api.post("/auth/login", { email, senha });
    localStorage.setItem("jwt", data.token);
    setUser({ email });           // guarde +infos se quiser
  }

  function logout() {
    localStorage.removeItem("jwt");
    setUser(null);
  }

  return (
    <AuthCtx.Provider value={{ user, login, logout }}>
      {children}
    </AuthCtx.Provider>
  );
}