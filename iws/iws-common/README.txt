The Common module serves as a central place to collect common functionality and
logic. Primarily, the module will contain Logic, which is used in 2 or more
other modules, but where the dependencies cannot be used, f.x. Logic which
belongs in both the Core & FitNesse module, cannot be stored in either as the
other won't be able to access it.

The second important aspect, is that all third party libraries that aren't
considered essential, should be wrapped here, so a later judgment can be made if
the module should be replaced with a different one, self implemented if can be
completly removed. By adding a wrapper, it also means that behaviour changes
between different versions can be easily controlled.