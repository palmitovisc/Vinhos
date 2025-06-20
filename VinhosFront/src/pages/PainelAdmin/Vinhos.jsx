import { useEffect, useState } from 'react';
import * as apiVinhos from '../../services/vinhos';
import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { motion } from 'framer-motion';

export default function PainelVinhos() {
  const empty = { nome: '', tipo: 'TINTO', safra: '', notas: '' };
  const [list, setList] = useState([]);
  const [form, setForm] = useState(empty);
  const [editingId, setEditingId] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    apiVinhos.getAllVinhos().then((d) => {
      setList(d);
      setLoading(false);
    });
  }, []);

  async function save(e) {
    e.preventDefault();
    if (editingId) {
      await apiVinhos.updateVinho(editingId, form);
    } else {
      await apiVinhos.createVinho(form);
    }
    setForm(empty);
    setEditingId(null);
    setList(await apiVinhos.getAllVinhos());
  }

  async function del(id) {
    if (confirm('Excluir vinho?')) {
      await apiVinhos.deleteVinho(id);
      setList(list.filter((v) => v.id !== id));
    }
  }

  if (loading) return <p>Carregando…</p>;

  return (
    <div className="space-y-8">
      <h1 className="text-2xl font-bold">Vinhos</h1>

      {/* Formulário */}
      <Card className="max-w-xl">
        <CardContent className="p-6">
          <form onSubmit={save} className="space-y-4">
            <div className="grid grid-cols-1 gap-3 sm:grid-cols-2">
              <input
                value={form.nome}
                onChange={(e) => setForm({ ...form, nome: e.target.value })}
                placeholder="Nome"
                className="rounded border p-2"
                required
              />
              <select
                value={form.tipo}
                onChange={(e) => setForm({ ...form, tipo: e.target.value })}
                className="rounded border p-2"
              >
                <option value="TINTO">Tinto</option>
                <option value="BRANCO">Branco</option>
                <option value="ROSÉ">Rosé</option>
              </select>
              <input
                type="number"
                value={form.safra}
                onChange={(e) => setForm({ ...form, safra: e.target.value })}
                placeholder="Safra"
                className="rounded border p-2"
              />
              <textarea
                value={form.notas}
                onChange={(e) => setForm({ ...form, notas: e.target.value })}
                placeholder="Notas"
                className="rounded border p-2 sm:col-span-2"
              />
            </div>
            <Button>{editingId ? 'Atualizar' : 'Cadastrar'}</Button>
            {editingId && (
              <Button type="button" variant="ghost" className="ml-4" onClick={() => setEditingId(null)}>
                Cancelar
              </Button>
            )}
          </form>
        </CardContent>
      </Card>

      {/* Lista */}
      <motion.table layout className="w-full text-left text-sm">
        <thead>
          <tr className="border-b border-zinc-200 dark:border-zinc-700">
            <th className="py-2 pr-4">Nome</th>
            <th className="py-2 pr-4">Tipo</th>
            <th className="py-2 pr-4">Safra</th>
            <th className="py-2 pr-4"></th>
          </tr>
        </thead>
        <tbody>
          {list.map((v) => (
            <motion.tr key={v.id} layout initial={{ opacity: 0 }} animate={{ opacity: 1 }} className="border-b border-zinc-100/50 dark:border-zinc-800/50">
              <td className="py-2 pr-4">{v.nome}</td>
              <td className="py-2 pr-4">{v.tipo}</td>
              <td className="py-2 pr-4">{v.safra}</td>
              <td className="py-2 pr-4 space-x-2">
                <Button size="sm" variant="outline" onClick={() => { setEditingId(v.id); setForm(v); }}>
                  Editar
                </Button>
                <Button size="sm" variant="destructive" onClick={() => del(v.id)}>
                  Excluir
                </Button>
              </td>
            </motion.tr>
          ))}
        </tbody>
      </motion.table>
    </div>
  );
}