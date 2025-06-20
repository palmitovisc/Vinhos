import { Link, NavLink } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { Menu, X } from 'lucide-react';
import { useState } from 'react';

export default function Navigation() {
  const { user, logout } = useAuth();
  const [open, setOpen] = useState(false);

  const linkClass = ({ isActive }) =>
    `px-3 py-2 rounded-2xl transition hover:bg-rose-100 dark:hover:bg-zinc-800 ${
      isActive ? 'font-semibold text-rose-600 dark:text-rose-400' : ''
    }`;

  return (
    <header className="sticky top-0 z-40 w-full bg-white/70 backdrop-blur dark:bg-zinc-900/70 shadow-sm dark:shadow-none">
      <div className="mx-auto flex max-w-6xl items-center justify-between px-6 py-4">
        {/* Brand */}
        <Link to="/" className="text-xl font-bold tracking-tight">
          🍷 Vinhos
        </Link>

        {/* Desktop links */}
        <nav className="hidden gap-3 md:flex">
          <NavLink to="/" className={linkClass} end>
            Início
          </NavLink>
          <NavLink to="/vinhos" className={linkClass}>
            Vinhos
          </NavLink>
          {user && user.role === 'ADMIN' && (
            <NavLink to="/admin/vinhos" className={linkClass}>
              Painel
            </NavLink>
          )}
        </nav>

        {/* Auth buttons */}
        <div className="hidden md:block">
          {user ? (
            <button onClick={logout} className="text-sm text-rose-500 hover:underline">
              Sair
            </button>
          ) : (
            <Link to="/login" className="text-sm hover:underline">
              Entrar
            </Link>
          )}
        </div>

        {/* Mobile burger */}
        <button className="md:hidden" onClick={() => setOpen(!open)}>
          {open ? <X size={24} /> : <Menu size={24} />}
        </button>
      </div>

      {/* Mobile sheet */}
      {open && (
        <nav className="md:hidden space-y-2 px-6 pb-6">
          <NavLink to="/" className={linkClass} onClick={() => setOpen(false)} end>
            Início
          </NavLink>
          <NavLink to="/vinhos" className={linkClass} onClick={() => setOpen(false)}>
            Vinhos
          </NavLink>
          {user && user.role === 'ADMIN' && (
            <NavLink to="/admin/vinhos" className={linkClass} onClick={() => setOpen(false)}>
              Painel
            </NavLink>
          )}
          <div>
            {user ? (
              <button onClick={logout} className="text-sm text-rose-500 hover:underline">
                Sair
              </button>
            ) : (
              <Link to="/login" onClick={() => setOpen(false)} className="text-sm hover:underline">
                Entrar
              </Link>
            )}
          </div>
        </nav>
      )}
    </header>
  );
}
