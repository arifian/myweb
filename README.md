# myweb
Various basic clojure web development implementations.<br><br>

<p>The app was developed in series of development phase.
Each folder represent each phase of feature/subsystem addition or a change in abstraction of the web app.
Thus the changes in each phase would be easily noticeable by beginners.</p>

Credits:
Had the initial system based on below repo, and then expanded, refractored, and added more functionality<br><br>
https://github.com/BamWidyat/blog-web <br>
https://github.com/BamWidyat/blog-datom <br>
https://github.com/BamWidyat/test-datom

## Usage

lein repl inside a folder <br><br>

01 exp       : Sandbox for testing new library and functions and such.<br>
02 webtest   : Basic example for creating a web service using hiccup, and bootstrap in clojure. <br>
03 blogdatom : Web service with datomic.<br>
04 blogdualdb: Switchable db, atom & datomic.<br>
05 blogalfa  : Adding system component lifecycle. (current phase)

## License

Copyright © 2017 Arifian Rahardianda

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
