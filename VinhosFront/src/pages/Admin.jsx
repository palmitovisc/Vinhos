import { Outlet, NavLink } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { Button } from '@/components/ui/button';

export default function Admin() {
  const { logout } = useAuth();

  const linkClass = ({ isActive }) =>
    `block rounded-lg px-3 py-2 hover:bg-rose-100 dark:hover:bg-zinc-800 ${
      isActive ? 'font-medium text-rose-600 dark:text-rose-400' : ''
    }`;

  return (
    <div className="flex min-h-[calc(100vh-64px)]">
      <aside className="w-64 shrink-0 border-r border-zinc-200 bg-white p-6 dark:border-zinc-800 dark:bg-zinc-950">
        <h2 className="mb-6 text-lg font-bold">Painel</h2>
        <nav className="space-y-1">
          <NavLink to="vinhos" className={linkClass}>
            Vinhos
          </NavLink>
          <NavLink to="clientes" className={linkClass}>
            Clientes
          </NavLink>
        </nav>
        <Button variant="ghost" className="mt-10 w-full justify-start" onClick={logout}>
          Sair
        </Button>
      </aside>
      <main className="flex-1 p-8">
        <Outlet />
      </main>
    </div>
  );
}
