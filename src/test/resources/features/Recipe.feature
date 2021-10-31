Feature: Register a Recipe

  Scenario Outline: As a nutritionist i want to post a new recipe.

    Given I register a new recipe as a nutritionist
    And I sending diet form to be created with name <name> , description <description>, ingredients <ingredients> and preparation <preparation>
    Then I should be able to see my newly created recipe

    Examples:
      | name      | description       | ingredients | preparation |
      | "Dieta 1" | "Comer verduras"  | "Frutas"    | "Licuar"    |