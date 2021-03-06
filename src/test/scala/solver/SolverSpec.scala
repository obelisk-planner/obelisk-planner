package solver

import org.scalatest.FlatSpec

class SolverSpec extends FlatSpec {

  "Solver" should "solve basic example" in {

    // Resources
    val waterResource = Resource(id = 1, name = "Water", measurementUnit = "Cup", naturalProduction = 1)
    val iceResource = Resource(id = 2, name = "Ice", measurementUnit = "Cube", naturalProduction = 0)
    val potTimeResource = Resource(id = 3, name = "Pot Time", measurementUnit = "Pot Month", naturalProduction = 1)
    val flowerResource = Resource(id = 4, name = "Flower", measurementUnit = "Item", naturalProduction = 0)

    // Recipes
    val freezingRecipe = Recipe(
      id = 1,
      name = "Freezing",
      production = List(
        ResourceProduction(
          resource = waterResource,
          production = -2,
        ),
        ResourceProduction(
          resource = iceResource,
          production = 3
        )
      ),
      utility = 0
    )

    val iceConsumptionRecipe = Recipe(
      id = 2,
      name = "Ice Consumption",
      production = List(
        ResourceProduction(
          resource = iceResource,
          production = -1,
        )
      ),
      utility = 1
    )

    val flowerGrowingRecipe = Recipe(
      id = 3,
      name = "Flower Growing",
      production = List(
        ResourceProduction(
          resource = waterResource,
          production = -1,
        ),
        ResourceProduction(
          resource = potTimeResource,
          production = -3,
        ),
        ResourceProduction(
          resource = flowerResource,
          production = 1,
        ),
      ),
      utility = 0
    )

    val flowerConsumptionRecipe = Recipe(
      id = 4,
      name = "Flower Consumption",
      production = List(
        ResourceProduction(
          resource = flowerResource,
          production = -1,
        )
      ),
      utility = 2
    )

    val recipes = List(freezingRecipe, iceConsumptionRecipe, flowerGrowingRecipe, flowerConsumptionRecipe)

    val result = Solver.solve(recipes)

    // Test
    assert(result.objectiveValue == 1.6666666666666665)
    assert(result.recipeSolutions(0).solution == 0.33333333333333337)
    assert(result.recipeSolutions(1).solution == 1.0)
    assert(result.recipeSolutions(2).solution == 0.3333333333333333)
    assert(result.recipeSolutions(3).solution == 0.3333333333333333)

    // TODO: These tests are failing because of order of recipes
    
    val reorderedRecipes = List(flowerGrowingRecipe, flowerConsumptionRecipe, freezingRecipe, iceConsumptionRecipe)

    val newResult = Solver.solve(reorderedRecipes)

    // Test
    assert(newResult.objectiveValue == 1.6666666666666665)
    assert(newResult.recipeSolutions(0).solution == 0.33333333333333337)
    assert(newResult.recipeSolutions(1).solution == 1.0)
    assert(newResult.recipeSolutions(2).solution == 0.3333333333333333)
    assert(newResult.recipeSolutions(3).solution == 0.3333333333333333)

  }

}

