# Things changed in the APis.
Throughout the code implementation, there are many things we can improve to make a readable and more fault-tolerant api. I will list all changes by file name.


# EmployeeController.java
Right from the start, we can already do some cleaning up.

```java
    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
```
Let's remove the "Autowired" annotation and instead make it final. Then let's give the controller the "RequiredArgsConstructor" annotation. Then we can get rid of that setter. For validation, let's add the validated annotation. Also, let's add a logger because we will replace the system.out.prinln with log.debug. In the end, we end up with this:
```java
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;
```
For the get employees api. At first, what was written may seem good but what if we have a long list of employees? Over 1000? We need to limit the user to a certain amout of employees they can get with each request. So something like this will work:
```java
@GetMapping("/employees")
    public EmployeesResponseDto getEmployees(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                             @RequestParam(value = "size", required = false, defaultValue = "25") int size) {
        return employeeService.retrieveEmployees(page, size);
    }
```
EmployeesResponseDto is a response object that holds a list of employees, the total pages, the current page, and the page size. This will make the frontend have an easier time to handle pagination as they won't need to calculate anything. Of course, this requires the service to handle this and you can refer to the code to see how we handled this!

```java
@GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name="employeeId") @Min(1) Long employeeId) throws EmployeeNotFoundException {
        return employeeService.getEmployee(employeeId);
    }
```
Moving on, the next method should add some sort of validation. A string or negative numbers should not be allowed to go through the api. If a non-number request comes, throw an invalid request error. Also, this method can now throw an EmployeeNotFoundException to alert the user that an employee doesn't exists.


```java
    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@Validated @RequestBody EmployeeDto createEmployeeDto){
        Employee employee = employeeService.saveEmployee(createEmployeeDto);
        log.debug("Employee Saved Successfully");
        return employee;
    }
```
Post requests needs the @RequestBody annotation. Also, it's best not to use System.out.prinln. Let's use log.debug to debugging information. 
Also, since we're following the REST guidelines, we must use 201 to indicate that an employee was created. 
Although we can use the employee entity, it would be best to create a dto(data transfer object) to handle requests. We don't want the user to create the id, we want the backend to create it(auto generated). Thus we should also return the employee object back so we know what id the employee is assigned.
```java
    @DeleteMapping("/employees/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable(name="employeeId") @Min(1) Long employeeId) throws EmployeeNotFoundException{
        employeeService.deleteEmployee(employeeId);
        log.debug("Employee with id {} was deleted successfully", employeeId);
    }
```

As for deleting employees,  once again, check that the employee id given was greater than or equal to 1. Also, inside, if the id doesn't exist, surpress the exception thrown by JPA and instead, throw our custom exception. Also, return a no content for deletion to indicate we're not returning any content even though it was updated successfully.

## EmployeeServiceImpl.java
One great thing about lombok is that it can do all the work for us.
Something like the following, while it works, it's a bit too wordy.
```java
    @Autowired
    private EmployeeRepository employeeRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
```
Instead, just add the "RequiredArgsConstructor" annotation like so:
```java
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
```
Then we can refactor like so:
```java
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    
    private final EmployeeRepository employeeRepository;
```
This is much more easier to read.


Originally, the code was like this. 
```java
public Employee getEmployee(Long employeeId) {
    Optional<Employee> optEmp = employeeRepository.findById(employeeId);
    return optEmp.get();
}
```
While I love the attempt of using Optional, force unwraping it makes the optional pointless. This will throw an internal error exception. Internal errors are best to be avoided. Thus we can easily do the following:
```java
public Employee getEmployee(Long employeeId) throws EmployeeNotFoundException {
        return employeeRepository
                .findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(String.format("Employee with id: %d not found", employeeId)
                        )
                );
    }
```


# Other minor changes.

1. I've added one additional api to patch update a single employee. While a put does the job great, it can be a bit of a pain to update all the fields, especially if it's an object that has many of them! So a patch is great for updating only a single field.

2. I've added test cases for the controller and service. It's best to *always* test your code. I've only done the happy cases due to time 
restriction and because this is just a mock api.

3. I added a GlobalExceptionHandler to handle common errors. While it's okay to let spring handle it, you might want to surpress certain exceptions. Thus the need for this, especially in production.

4. It's a bit pointless to have a @Getter and @Setter annotation in each field of the employee class. Thus we can just add an annotation called `Data` above the Employee class

# Other ways to improve!
Of course, there are other ways to improve this api for example, instead of `@Min(1)  Long employeeId`, we could have implemented a resolver that checks if the value is a Long, get the employee from the repository, and return it right it away! Something like `@EmployeeResolver Employee employee`. The only reason I didn't add it was it seemed a bit unnecessary at this step since only two methods would have used it. 