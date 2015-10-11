(ns hiragana.core 
	(:require [instaparse.core :as insta])
	(:import javax.swing.JOptionPane))

(def romhir {"a" "\u3042" ,"i" "\u3044" ,"u" "\u3046" ,"e" "\u3048" ,"o" "\u304A",
             "ka" "\u304B","ga" "\u304C","ki" "\u304D","gi" "\u304E","ku" "\u304F","gu" "\u3050","ke" "\u3051","ge" "\u3052","ko" "\u3053","go" "\u3054",
             "sa" "\u3055","za" "\u3056","si" "\u3057","zi" "\u3058","su" "\u3059","zu" "\u305A","se" "\u305B","ze" "\u305C","so" "\u305D","zo" "\u305E",
             "ta" "\u305F","da" "\u3060","ti" "\u3061","di" "\u3062","x" "\u3063","tu" "\u3064","du" "\u3065","te" "\u3066","de" "\u3067","to" "\u3068","do" "\u3069"
             "na" "\u306A","ni" "\u306B","nu" "\u306C","ne" "\u306D","no" "\u306E",
             "ha" "\u306F","ba" "\u3070","pa" "\u3071","hi" "\u3072","bi" "\u3073","pi" "\u3074",
             "hu" "\u3075","bu" "\u3076","pu" "\u3077","he" "\u3078","be" "\u3079","pe" "\u307A","ho" "\u307B","bo" "\u307C","po" "\u307D",
             "ma" "\u307E","mi" "\u307F","mu" "\u3080","me" "\u3081","mo" "\u3082",
             "xa" "\u3083","ya" "\u3084","xu" "\u3085","yu" "\u3086","xo" "\u3087","yo" "\u3088",
             "ra" "\u3089","ri" "\u308A","ru" "\u308B","re" "\u308C","ro" "\u308D",
             "wa" "\u308F","wo" "\u3092","n" "\u3093"
             }); almost full unicode hiragana table; x stands for small kana (see cykana and sokun below)

(def hgp
	(insta/parser (clojure.java.io/resource "hiragana.bnf")) ); no provision for parse errors !

(defn sok[_] "x") ; double consonants transformed to a small 'tu'
(defn ryx[x] (clojure.string/replace x #"y" "ix")) ; kyo transformed to kixo, syo to sixo, ...
(def  cyk (comp ryx str)) ; insta/transform function for kyo, syo, tyo, ...
(defn tr[x] (insta/transform  {:h str, :cykana cyk,:sokuon sok} (hgp x))) ; :h =  standard kana
                                                                          ; :cykana = kyo, syo, ...
                                                                          ; :sokuon = double consonants

(defn split[x] (list (str (nth x 0)(nth x 1)) (str (nth x 2)(nth x 3)))); kixo split to ki,xo, ...
(defn csplit[x](if (= 4 (count x))(split x) x)); detect 'kixo' type strings and apply split to them
(defn tr2[x]  (flatten (map csplit x))) ; find all strings of type 'kixo','sixo',... and split them
(defn tokana[s] (romhir s s)) ; if s is in romhir's keys, return the unicode value, else return s
(defn hir [x] (apply str (map tokana (tr2 (tr x))))); finally, transform latin character chain to hiragana
;; ================== Interface =========================
(defn -main[]
   (let [chaine (JOptionPane/showInputDialog nil "Enter romaji:")]
    	(JOptionPane/showInputDialog nil "Hiragana:" (hir chaine))))

