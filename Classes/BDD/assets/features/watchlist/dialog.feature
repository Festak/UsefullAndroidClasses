Feature: Dialog for create Filter
    Validate dialog for create Filter

    Scenario Outline: Validate Dialog on button enabled
        Given I have a SignInActivity for WatchList
        # if login first time  When I login with email <email> password <password> smscode <smscode> pincode <pin>
        When I login with email <email> password <password> smscode <smscode> pincode <pin>
          # User enters MainActivity
          And I go to MarginWatchListsActivity
          # User enters MarginWatchListsActivity
          And I click on view with id [addFloatingActionButton]
          # Input Dialog shown
          And I fill any data "<text>" into the view with id [input]
        Then I should see common dialog button POSITIVE enabled <enabled>

        Examples:
        | email                   | password  | pin     | smscode | text    | enabled   |
        | fiatskovich.w@gmail.com | 1q2w3e4r  | 0 0 0 0 | 0000    | qqq     | false     |
        | fiatskovich.w@gmail.com | 1q2w3e4r  | 0 0 0 0 | 0000    |         | false     |
        | fiatskovich.w@gmail.com | 1q2w3e4r  | 0 0 0 0 | 0000    |  test   | true      |

    Scenario Outline: Validate Dialog on error showed
        Given I have a SignInActivity for WatchList
        # if login first time  When I login with email <email> password <password> smscode <smscode> pincode <pin>
        When I login with email <email> password <password> smscode <smscode> pincode <pin>
          # User enters MainActivity
          And I go to MarginWatchListsActivity
          # User enters MarginWatchListsActivity
          And I click on view with id [addFloatingActionButton]
          # Input Dialog shown
          And I fill any data "<text>" into the view with id [input]
        Then I should see text input layout error <showed> with id [inputWrapper] and stringId [watch_list_dialog_validation_message]

        Examples:
        | email                   | password  | pin     | smscode | text    | showed   |
        | fiatskovich.w@gmail.com | 1q2w3e4r  | 0 0 0 0 | 0000    | qqq     | true     |
        | fiatskovich.w@gmail.com | 1q2w3e4r  | 0 0 0 0 | 0000    | ggg     | false    |