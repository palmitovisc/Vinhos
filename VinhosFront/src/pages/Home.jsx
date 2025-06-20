import { useEffect, useState } from 'react';
import * as apiVinhos from '../services/vinhos';
import WineCard from '../components/WineCard';
import { Spinner } from '../components/Spinner';
import { motion } from 'framer-motion';

export default function Home() {
  const [list, setList] = useState(null);

  useEffect(() => {
    apiVinhos.getAllVinhos().then(setList);
  }, []);

  return (
    <section>
      {/* Hero */}
      <div className="mb-10 text-center space-y-4">
        <h1 className="text-4xl font-extrabold tracking-tight sm:text-5xl">
          Descubra vinhos extraordinários
        </h1>
        <p className="mx-auto max-w-xl text-lg opacity-80">
          Catálogo selecionado com as melhores safras. Explore nossa coleção e encontre o vinho perfeito.
        </p>
      </div>

      {/* Lista */}
      {list ? (
        <motion.div
          layout
          className="grid grid-cols-1 gap-6 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4"
        >
          {list.map((v) => (
            <motion.div key={v.id} layout initial={{ opacity: 0 }} animate={{ opacity: 1 }}>
              <WineCard vinho={v} />
            </motion.div>
          ))}
        </motion.div>
      ) : (
        <div className="flex justify-center">
          <Spinner />
        </div>
      )}
    </section>
  );
}