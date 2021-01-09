# init memory
lui $t0, 4096
ori $t0, $t0, 64
lui $t1, 4386
ori $t1, $t1, 13124
sw $t1, 0($t0)
addi $t0, $zero, 0
addi $t1, $zero, 0
# init register
lui $s0, 4096
ori $s0, $s0, 64
# initial operation completed
lw $t0,0($s0)
lbu $t1,1($s0)
lb $t2,2($s0)
lh $t3,2($s0)
sw $t0,8($s0)
sh $t0,0xc($s0)
sb $t0,0x10($s0)
