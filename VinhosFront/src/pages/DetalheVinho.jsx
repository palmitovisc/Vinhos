import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Spinner } from '../components/Spinner';
import * as apiVinhos from '../services/vinhos';
import { motion } from 'framer-motion';

export default function DetalheVinho() {
  const { id } = useParams();
  const [vinho, setVinho] = useState(null);

  useEffect(() => {
    apiVinhos.getVinho(id).then(setVinho);
  }, [id]);

  if (!vinho) {
    return (
      <div className="flex justify-center">
        <Spinner />
      </div>
    );
  }

  return (
    <motion.article initial={{ opacity: 0 }} animate={{ opacity: 1 }} className="space-y-6">
      <img
        src={vinho.imagemUrl || '/placeholder-wine.jpg'}
        alt={vinho.nome}
        className="mx-auto h-80 w-auto rounded-2xl object-cover shadow-lg"
      />
      <h1 className="text-3xl font-bold">{vinho.nome}</h1>
      <p className="opacity-80">
        <strong>Tipo:</strong> {vinho.tipo} | <strong>Safra:</strong> {vinho.safra || '‑'}
      </p>
      {vinho.notas && <p className="leading-relaxed">{vinho.notas}</p>}
    </motion.article>
  );
}