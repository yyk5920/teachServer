lui $t0, 4097
ori $t0, $t0, 0
addi $t1, $zero, 0
sw $t1, 0($t0)
addi $t0, $zero, 0
addi $t1, $zero, 0
lui $t0, 4097
ori $t0, $t0, 4
addi $t1, $zero, 0
sw $t1, 0($t0)
addi $t0, $zero, 0
addi $t1, $zero, 0
addi $t0, $zero, 0
addi $t1, $zero, 0
addi $s0, $zero, 3 # a = 3
addi $s1, $zero, 4 # b = 4
addi $s3, $zero, 1 # c = 1
addi $s4, $zero, 2 # d = 2
add $t1, $s0, $s1 # (a + b)
add $t2, $s3, $s4 # (c + d)
sub $s4, $t1, $t2 # (c + d) - (a + b) = 
