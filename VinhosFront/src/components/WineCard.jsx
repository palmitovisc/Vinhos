import { Card, CardContent, CardFooter } from '@/components/ui/card';
import { Link } from 'react-router-dom';

export default function WineCard({ vinho }) {
  return (
    <Card className="overflow-hidden transition hover:shadow-lg">
      <img
        src={vinho.imagemUrl || '/placeholder-wine.jpg'}
        alt={vinho.nome}
        className="h-48 w-full object-cover"
      />
      <CardContent className="space-y-1 py-4">
        <h3 className="font-semibold truncate" title={vinho.nome}>
          {vinho.nome}
        </h3>
        <p className="text-sm text-zinc-500 dark:text-zinc-400">
          {vinho.tipo} • Safra {vinho.safra || '‑'}
        </p>
      </CardContent>
      <CardFooter className="py-3">
        <Link to={`/vinho/${vinho.id}`} className="text-sm text-rose-600 hover:underline">
          Ver detalhes →
        </Link>
      </CardFooter>
    </Card>
  );
}

// src/components/Spinner.jsx
export function Spinner() {
  return (
    <div className="h-10 w-10 animate-spin rounded-full border-4 border-dashed border-rose-500"></div>
  );
}