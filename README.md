# hiragana

A Clojure script designed to convert a sequence of latin transcription (using a modified nihonshiki) into hiragana.

Uses Instaparse for parsing the initial string and converting it (see [resource file](resources/hiragana.bnf) )

Based on Instaparse's PEG implementation.

A quick and dirty 'proof of concept' version

TO DO:
- treat parse errors
- improve parsing quality
- cleaner graphical interface

## Usage

cd .../hiragana (the directory where project.clj is located)

lein run

Enter a valid sequence of nihonshiki, plus ENTER

The hiragana gets displayed in a JOptionPane

Main characteristics of nihonshiki (see [nihonshiki](https://en.wikipedia.org/wiki/Nihon-shiki_romanization) ):
- si (not shi)
- tu (not tsu)
- hu (not fu)
- du or zu, according to desired transcription
- sya, tya (not sha, cha)
- zya, dya (not ja)
Specific of this version of nihonsiki:
- long vowels: aa, ii, uu, ei, ou

## License

Copyright © 2015 Francis Stephan	

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
