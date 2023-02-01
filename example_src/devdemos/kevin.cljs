(ns devdemos.kevin
    (:require
     [devcards.core]
     [om.core :as om :include-macros true]
     [om.dom :as dom :include-macros true]
     [reagent.core :as reagent]
     [clojure.string :as string]
     [sablono.core :as sab :include-macros true]
     [cljs.test :as t :include-macros true :refer-macros [testing is]])
    (:require-macros
     ;; Notice that I am not including the 'devcards.core namespace
     ;; but only the macros. This helps ensure that devcards will only
     ;; be created when the :devcards is set to true in the build config.
     [devcards.core :as dc :refer [defcard defcard-doc noframe-doc deftest dom-node]]))

(defn playbook-choose-random
  "Returns a random playbook"
  []
  (def playbooks [
    :cutter :hound :leech :lurk :slide :spider :whisper
  ])
  ;; https://clojuredocs.org/clojure.core/rand-nth
  (rand-nth playbooks)
)

(defn playbook-add
  "Adds a playbook to a scoundrel"
  [scoundrel-map]
  (def playbook-new (playbook-choose-random))
  ;; https://clojuredocs.org/clojure.core/assoc
  (assoc scoundrel-map :playbook playbook-new)
)

(defn heritage-choose-random
  "Returns a random heritage"
  []
  (def heritages [
    :skovlan :iruvian :akoros :dagger-isles :severos :tycheros
  ])
  (rand-nth heritages)
)

(defn heritage-add
  "Adds a heritage to a scoundrel"
  [scoundrel-map]
  (def heritage-new (heritage-choose-random))
  (assoc scoundrel-map :heritage heritage-new)
)

(defn background-choose-random
  "Returns a random background"
  []
  (def backgrounds [
    :academic :labor :law :trade :military :noble :underworld
  ])
  (rand-nth backgrounds)
)

(defn background-add
  "Adds a background to a scoundrel"
  [scoundrel-map]
  (def background-new (background-choose-random))
  (assoc scoundrel-map :background background-new)
)

(defn vice-choose-random
  "Returns a random vice"
  []
  (def vices [
    :faith :gambling :luxury :obligation :pleasure :stupor :weird
  ])
  (rand-nth vices)
)

(defn vice-add
  "Adds a vice to a scoundrel"
  [scoundrel-map]
  (def vice-new (vice-choose-random))
  (assoc scoundrel-map :vice vice-new)
)

(defn create-scoundrel
  "Create a new scoundrel"
  []
  (vice-add (background-add (heritage-add (playbook-add {}))))
)

; (defcard
;   (fn [data-atom owner]
;     (sab/html [:div [:h3 "Example Counter: " (:count @data-atom)]
;                [:button {:onClick (fn [] (swap! data-atom update-in [:count] inc))} "inc"]])))
; (defcard
;   (fn [data-atom owner]
;     (sab/html
;       [:div
;         [:h3 "Example Counter: " (:count @data-atom)]
;         [:button
;           {:onClick (fn [] (swap! data-atom update-in [:count] inc))}
;           "inc"
;         ]
;       ]
;     )
;   )
; )
(defcard scoundrel
  (fn [data-atom owner]
    (sab/html
      [:div
        [:h3 "Generated Scoundrel #" (:count @data-atom)]
        [:p "Scoundrel: "];  (:scoundrel @data-atom)]
        [:button
          {:onClick (fn []
            (swap! data-atom update-in [:count] inc)
            (swap! data-atom update-in [:scoundrel] create-scoundrel)
          )}
          "New"
        ]
      ]
    )
  )
  {
    :scoundrel {}
    :count      0
  }
  {
    :inspect-data true
    :heading      true
  }
)

(defn create-clock
  "Creates a new clock"
  []
  (def numerator (rand-int 20))
  (def denominator (+ numerator (rand-int 20)))
  {
    :numerator   numerator
    :denominator denominator
  }
)

(defn clock-inc
  "Increments a given clock"
  [clock]
  (def numerator-new
    (inc (:numerator clock))
  )
  {
   :numerator   numerator-new
   :denominator (:denominator clock)
  }
)

(defcard clocks-clocks-clocks
  (fn [data-atom owner]
    (sab/html
      [:div
       [:h3 "Clock #" (:count @data-atom)]
       [:p "Clock: "]
       [:button
        {:onClick (fn []
          (swap! data-atom update-in [:count] inc)
          (swap! data-atom update-in [:clock] create-clock)
        )}
        "New Clock"
       ]
       [:button
        {:onClick (fn []
          ;; (def clock-new (update-in (:clock @data-atom) [:numerator] inc))
          ;; (print clock-new)
          (swap! data-atom update-in [:clock :numerator] inc)
        )}
        "Inc Clock"
       ]
      ]
    )
  )
  {
   :clock {
     :numerator   1
     :denominator 4
   }
   :count   0
  }
  {
   :inspect-data true
   :heading      true
  }
)

(defcard-doc
  "#It all starts with `defcard`

   Once you have Devcards setup and have required the devcards macros as below
   ```clojure
   (:require-macros
     [devcards.core :as dc :refer [defcard]])
   ```
   You can then use the `defcard` macro. `defcard` is a multipurpose
   macro which is designed to take what you are working on elevate
   live into the browser. It can handle many types of data but
   primarily takes any type of ReactElement.

   So this would be the \"Hello World\" of Devcards,"

  '(defcard (sab/html [:h3 "Hello world"]))

  "You can see this devcard rendered below:")

(defcard (sab/html [:h3 "Hello World!"]))

(defcard
  "# These cards are hot ... loaded

  One thing that isn't easy to see from reading this page is that when
  you define a Devcard in your code and save it, a card instantly
  appears in the Devcards interface. It shows up on the page in the
  order of its definition, and when you comment out or delete the
  card from your code it dissapears from the interface.

  ## `defcard` takes 5 arguments

  * **name** an optional symbol name for the card to be used as a heading and to
      locate it in the Devcards interface
  * **documentation** an optional string literal of markdown documentation
  * **main object** a required object for the card to display 
  * **initial data** an optional Atom, RAtom or Clojure data structure (normally 
    a Map), used to initialize the a state that the devcard will pass back to 
    your code examples. More on this later ...
  * **devcard options** an optional map of options for the underlying devcard 

  ```
  (defcard hello-world                     ;; optional symbol name
    \"**Optional Mardown documentation**\"   ;; optional literal string doc
    {:object \"of focus\"}                   ;; required object of focus
    {}                                     ;; optional intial data
    {}                                     ;; optional devcard config
  ) 
  ```

  We are going to explore these arguments and examples of how they work
  below. 

  This is pretty *meta* as I am using Devcards to document Devcards.
  So please take this page as an example of **interactive literate
  programming**. Where you create a story about your code, supported
  by live examples of how it works.

  ## What's in a name?

  The first optional arg to `defcard` is the **name**. This is a
  symbol and it is used to provide a distinct key for the card
  you are creating. 

  For cards that aren't stateful like documentation and such the name
  really isn't necessary but when you create cards that are displaying
  stateful running widgets then this key will help ensure that the
  underlying state is mapped back to the correct card.
  
  The name will be used to create a header on the card. The header can
  be clicked to display and work on the card by itself.

  For instance here is a card with a name:
 
  ```
  (defcard first-example)
  ```
   
  You can see this card with its header just below. If you click on
  the `first-example` card header, you will be presented with the card
  by itself, so that you can work on the card in isolation.

  
")

(defcard first-example
  (sab/html [:div])
  {}
  {:heading true})

(defcard-doc
  "## Name absentia

   In the absense of a name, the heading of the card will not be displayed.

   Devcards generate's a card name in the order that it shows up on
   the page. You can see this autogenerated name by setting the
   `:heading` option to `true`.

   ```
   (defcard {}               ; main obj
            {}               ; initial data
            {:heading true}) ; devcard options: forces header to show
   ```

   Which is displayed as so:")

(defcard {} {} {:heading true})

(defcard
  "The generated card name above is *card-4*. This makes sense
  because it's the fouth card on the page that has no name.

  The generated card name will work in many cases but not for all.
  It's best to have a name for cards with state.")

(defcard
  "## Optional markdown doc

  You can also add an optional markdown documentation to your card like this:
  ```
  (defcard example-2 \"## Example: This is optional markdown\")
  ```
  ")

(defcard example-2 "## Example: This is optional markdown")

(defcard
  "Since the name `example-2` is optional you can write docs just like this:

   ```
   (defcard 
     \"## Example: writing markdown docs is intended to be easy.
       
      You should be able to add docs to your code examples easily.\")
   ```")

(defcard-doc
  "## A function as a main object

  The main point of devcards is to get your code out of the source
  file and up and running in front of you as soon as possible. To this
  end devcards tries to provide several generic ways for you to run
  your code in the devcards interface. The main way is to pass a
  function to the `defcard` macro as the main object.
   
  Instead of a ReactElement you can provide a function the takes two
  parameters and returns a ReactElement like so:"

  (dc/mkdn-pprint-code
   '(defcard (fn [data-atom owner]
               (sab/html [:div [:h2 "Example: fn that returns React"]
                          (prn-str data-atom)]))))
  "In this example the `data-atom` is a ClojureScript Atom and
  the`owner` is the enclosing cards ReactElement.")

(defcard
  (fn [data-atom owner]
    (sab/html [:div [:h3 "Example: fn that returns React"]
               (prn-str data-atom)])))

(defcard-doc
  "If `data-atom` in the above example changes then the card will be re-rendered.
  
   Let's make a quick example counter:"
    (dc/mkdn-pprint-code
     '(defcard
        (fn [data-atom owner]
          (sab/html [:div [:h3 "Example Counter: " (:count @data-atom)]
                     [:button {:onClick (fn [] (swap! data-atom update-in [:count] inc))} "inc"]])))))

(defcard
  (fn [data-atom owner]
    (sab/html [:div [:h3 "Example Counter: " (:count @data-atom)]
               [:button {:onClick (fn [] (swap! data-atom update-in [:count] inc))} "inc"]])))

(defcard-doc
  "## Initial state

  The counter example above was very interesting but what if you want
  to introduce some initial state?

  Well the next option after the main object is the **initial-data**
  parameter. You can use it like so:"
  (dc/mkdn-pprint-code
   '(defcard
      (fn [data-atom owner]
        (sab/html [:div [:h3 "Example Counter w/Initial Data: " (:count @data-atom)]
                   [:button {:onClick (fn [] (swap! data-atom update-in [:count] inc))} "inc"]]))
      {:count 50})))

(defcard
  (fn [data-atom owner]
    (sab/html [:div [:h3 "Example Counter w/Initial Data: " (:count @data-atom)]
               [:button {:onClick (fn [] (swap! data-atom update-in [:count] inc))} "inc"]]))
  {:count 50})

(defcard-doc
  "## Initial state can be an Atom

  You can also pass an Atom as the initial state. This is a very
  important feature of devcards as it allows you to share state
  between cards.

  The following examples share state:"

  (dc/mkdn-pprint-code
   '(defonce first-example-state (atom {:count 55})))

  (dc/mkdn-pprint-code
   '(defcard example-counter
      (fn [data-atom owner]
        (sab/html [:h3 "Example Counter w/Shared Initial Atom: " (:count @data-atom)]))
      first-example-state))
  
  (dc/mkdn-pprint-code
   '(defcard example-incrementer
      (fn [data-atom owner]
        (sab/html [:button {:onClick (fn [] (swap! data-atom update-in [:count] inc))} "increment"]))
      first-example-state))

  (dc/mkdn-pprint-code
   '(defcard example-decrementer
      (fn [data-atom owner]
        (sab/html [:button {:onClick (fn [] (swap! data-atom update-in [:count] dec))} "decrement"]))
      first-example-state))
  "As you can see, we created three cards that all share the same state.

   If you try these example cards below you will see that they are all wired together:")

(defonce first-example-state (atom {:count 55}))

(defcard example-counter
  (fn [data-atom owner]
    (sab/html [:h3 "Example Counter w/Shared Initial Atom: " (:count @data-atom)]))
  first-example-state)

(defcard example-incrementer
  (fn [data-atom owner]
    (sab/html [:button {:onClick (fn [] (swap! data-atom update-in [:count] inc)) } "increment"]))
  first-example-state)

(defcard example-decrementer
  (fn [data-atom owner]
    (sab/html [:button {:onClick (fn [] (swap! data-atom update-in [:count] dec)) } "decrement"]))
  first-example-state)

(defcard
  "# Reseting the state of a card

  The **initial state** is just the initial state of the card. What if
  you want to reset the card and start from the initial state or some
  new initial state?

  There is a simple trick: you just change the name of the card. I
  often add and remove a `*` at the end of a card name to bump the
  state out of the card in read in a new initial state.

  I am debating adding knobs for these things to the heading panel of
  the card. A knob to reset the state, a knob to turn on history, a
  knob to display the data in the atom. Let me know if you think this
  is a good idea.")

(defcard
  "# Devcard options

  The last argument to `defcard` is an optional map of options.
 
  Here are the available options with their defaults:

  ```
  { 
    :frame true         ;; whether to enclose the card in a padded frame
    :heading true       ;; whether to add a heading panel to the card
    :padding true       ;; whether to have padding around the body of the card
    :hidden false       ;; whether to diplay the card or not
    :inspect-data false ;; whether to display the data in the card atom
    :watch-atom true    ;; whether to watch the atom and render on change 
    :history false      ;; whether to record a change history of the atom
    :classname \"\"       ;; provide card with a custom classname
    :projection identity ;; provide a projection function for card state
  }
  ```

  Most of these are fairly straight forward. Whats important to know
  is that you can change any of these live and the card will respond
  with the new behavior.

  Here are some cards that exercise these options:")

(defcard no-framed
  (str "## This is a devcard

       And it doesn't have a frame")
  {}
  {:frame false})

(defcard no-heading
  (str "# this card is hiding its heading")
  {}
  {:heading false})

(defcard no-padding
  (str " this card is has no padding on its body")
  {}
  {:padding false})

(defcard custom-classname
  (str " this card has a custom class `.red-box`")
  {}
  {:classname "red-box"})

(defcard inspect-data
  (fn [data-atom owner]
    (sab/html [:div [:h3 "Inspecting data on this Counter: " (:count @data-atom)]
               [:button {:onClick (fn [] (swap! data-atom update-in [:count] inc))} "inc"]]))
  {:count 50}
  {:inspect-data true})

(defcard inspect-data-and-record-history
  (fn [data-atom owner]
    (sab/html [:div [:h3 "Inspecting data and recording history this Counter: " (:count @data-atom)]
               [:button {:onClick (fn [] (swap! data-atom update-in [:count] inc))} "inc"]]))
  {:count 50}
  {:inspect-data true :history true})

(defcard project-data
  (fn [data-atom _]
    (sab/html [:div [:h3 "Inspecting data but only some of the data: Counter" (:count @data-atom)]
               [:button {:onClick (fn [] (swap! data-atom update-in [:count] inc))} "inc"]
               [:div "Random other state that is not important: " (:whatever @data-atom)]]))
  {:count 50
   :whatever "this state is present but not shown in `inspect-data` part."}
  {:inspect-data true
   :projection (fn [state] (select-keys state [:count]))})


(defcard-doc
  "## Accessing the DOM with `dom-node`

   While Devcards was written in and are very easy to use in
   conjunction with React. You may want to write something that writes
   directly to the DOM.

   The helper macro `dom-node` takes a function that accepts a DOM
   node and ClojureScript Atom and returns a ReactElement."

  (dc/mkdn-pprint-code
   '(defcard example-dom-node
      (dom-node (fn [data-atom node]
                  (set! (.-innerHTML node) "<h2>Example Dom Node</h2>"))))))

(defcard example-dom-node
  (dom-node
   (fn [data-atom node]
     (set! (.-innerHTML node) "<h2>Example Dom Node</h2>"))))
