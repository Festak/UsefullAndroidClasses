Feature: Actions with watch list

    Scenario Outline: Create item in watchlist
        Given I have a SignInActivity for WatchList
        # if login first time  When I login with email <email> password <password> smscode <smscode> pincode <pin>
        When I login with email <email> password <password> smscode <smscode> pincode <pin>
          # User enters MainActivity
          And I go to MarginWatchListsActivity
          # User enters MarginWatchListsActivity
          And I click on view with id [addFloatingActionButton]
          # Input Dialog shown
          And I fill any data "<text>" into the view with id [input]
          And I click on common dialog button POSITIVE
          # User enters WatchListFormActivity
          And I select items in list with id [recyclerViewMarginAssetPair] on positions <positions>
          And I click on view with id [action_done]
          # User enters MarginWatchListsActivity
        Then I should see margin with text "<text>"

        Examples:
        | email                   | password | pin     | smscode | text  | positions |
        | fiatskovich.w@gmail.com | 1q2w3e4r | 0 0 0 0 | 0000    | qqq14 | 0 1 2     |

    Scenario Outline: Edit item in watchlist
        Given I have a SignInActivity for WatchList
        # if login first time  When I login with email <email> password <password> smscode <smscode> pincode <pin>
        When I login with email <email> password <password> smscode <smscode> pincode <pin>
          # User enters MainActivity
          And I go to MarginWatchListsActivity
          # User enters MarginWatchListsActivity
          And I do long click on margin with recycler id [recyclerView] with text "<text>"
          # Dialog with actions shows
          And I click on view with stringId [edit]
          And I select items in list with id [recyclerViewMarginAssetPair] on positions <newpositions>
          And I click on view with id [action_done]
        Then I should see margin with text "<text>"

        Examples:
        | email                   | password | pin     | smscode | text  | newpositions |
        | fiatskovich.w@gmail.com | 1q2w3e4r | 0 0 0 0 | 0000    | qqq14 | 0 1 2 3      |

    Scenario Outline: Check is button enabled to edit item
        Given I have a SignInActivity for WatchList
        # if login first time  When I login with email <email> password <password> smscode <smscode> pincode <pin>
        When I login with email <email> password <password> smscode <smscode> pincode <pin>
          # User enters MainActivity
          And I go to MarginWatchListsActivity
          # User enters MarginWatchListsActivity
          And I do long click on margin with recycler id [recyclerView] with text "<text>"
          # Dialog with actions shows
          And I click on view with stringId [edit]
          And I select items in list with id [recyclerViewMarginAssetPair] on positions <newpositions>
        Then I should see view enabled <enabled> with id [action_done]

        Examples:
        | email                   | password | pin     | smscode | text  | newpositions | enabled |
        | fiatskovich.w@gmail.com | 1q2w3e4r | 0 0 0 0 | 0000    | qqq13 |              | false   |
        | fiatskovich.w@gmail.com | 1q2w3e4r | 0 0 0 0 | 0000    | qqq13 | 0 1          | true    |

    Scenario Outline: Check on count of selected items
        Given I have a SignInActivity for WatchList
        # if login first time  When I login with email <email> password <password> smscode <smscode> pincode <pin>
        When I login with email <email> password <password> smscode <smscode> pincode <pin>
          # User enters MainActivity
          And I go to MarginWatchListsActivity
          # User enters MarginWatchListsActivity
          And I click on view with id [addFloatingActionButton]
          # Input Dialog shown
          And I fill any data "<text>" into the view with id [input]
          And I click on common dialog button POSITIVE
          # User enters WatchListFormActivity
          And I select items in list with id [recyclerViewMarginAssetPair] on positions <positions>
          # User enters MarginWatchListsActivity
        Then I should see view with stringId [margin_watch_list_tab_selected_assets_formatted] and args <selectedcount>

        Examples:
        | email                   | password | pin     | smscode | text  | positions | selectedcount |
        | fiatskovich.w@gmail.com | 1q2w3e4r | 0 0 0 0 | 0000    | zzz2  | 0 1 2     | 3             |
        | fiatskovich.w@gmail.com | 1q2w3e4r | 0 0 0 0 | 0000    | zzz2  |           | 0             |

    Scenario Outline: Check on open search activity
        Given I have a SignInActivity for WatchList
        # if login first time  When I login with email <email> password <password> smscode <smscode> pincode <pin>
        When I login with email <email> password <password> smscode <smscode> pincode <pin>
          # User enters MainActivity
          And I go to MarginWatchListsActivity
          # User enters MarginWatchListsActivity
          And I click on view with id [addFloatingActionButton]
          # Input Dialog shown
          And I fill any data "<text>" into the view with id [input]
          And I click on common dialog button POSITIVE
          # User enters WatchListFormActivity
          And I click on view with id [action_search]
          # User enters MarginSearchAssetPairActivity
        Then I should see view with id [marginSearchAssetPair]

        Examples:
        | email                   | password | pin     | smscode | text  |
        | fiatskovich.w@gmail.com | 1q2w3e4r | 0 0 0 0 | 0000    | zzz2  |