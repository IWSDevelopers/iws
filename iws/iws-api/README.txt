The purpose of the IWS API module, is to provide the interface to use a running
IWS implementation. It consists of the functionality interfaces, and their
required Objects.

Since one of the possible implementations at design-time was a WebService or
REST, that requires a empty constructors and setters, it is not possible to
create the Objects with immutability in mind. This additionally means that from
a Thread-safety perspective, the classes will become tougher to implement. One
of the methods is then to support Object copying, using both Copy Constructors
and copying of mutable Object when setting & getting them.

The interfaces to access the functionality, is all located in the API package,
and all required objects are located in the sub-packages, "request", "response"
and "data" (Objects commonly used by either Request or Result or both).

Generally, all Request Objects must implement the Verifiable interface, this
allows for a simple mechanism to verify the correctness of the Request Objects.
Similarly all Result Objects must implement the Fallible interface (typically
done so by extending the AbstractResult class), which contains the default
information regarding the success/error from the operation.

The functionality is defined as methods with the following signature:

    MethodResponse method(AuthenticationToken, MethodRequest);

All functionality is defined in the interfaces located in the top-level package,
net.iaeste.iws.api. The Token that is required is central, since it must be
created when a session is started, and deprecated when the session is ending.
The methods for this are defined in the Access interface.

The primary implementation for all the funcationality is in the Core module,
which have a number of "Controllers", that act as the primary implementation and
will handle all the requests. Each interface in the API module has a
correspnding Controller. Additional implementations are found in the EJB module,
and again in the Client module. The Client module serves as the primary way to
work with the IWS, since it can be used to access the IWS either as a library
(useful for developing/testing), as a Local/Remote EJB or WebService. All
depending upon the configuration.
