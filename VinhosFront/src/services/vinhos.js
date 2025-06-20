// src/services/vinhos.js
export async function createVinho(body) {
  return (await api.post("/vinhos", body)).data;
}
export async function updateVinho(id, body) {
  return (await api.put(`/vinhos/${id}`, body)).data;
}
export async function deleteVinho(id) {
  return (await api.delete(`/vinhos/${id}`)).data;
}
